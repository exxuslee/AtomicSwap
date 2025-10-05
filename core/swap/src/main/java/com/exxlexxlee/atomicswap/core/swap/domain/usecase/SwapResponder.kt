package com.exxlexxlee.atomicswap.core.swap.domain.usecase

import android.util.Log
import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapBailTxListener
import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapBlockchain
import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapRedeemTxListener
import com.exxlexxlee.atomicswap.core.swap.domain.repository.SwapRepository
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.core.swap.model.SwapState

class SwapResponder(
    private val swapResponderDoer: SwapResponderDoer,
) {

    suspend fun processNext(swapState: SwapState) = when (swapState) {
        SwapState.Requested -> {
            Log.d("SwapKit", "SwapResponder1 REQUESTED")
            Unit
        }

        SwapState.Responded -> {
            Log.d("SwapKit", "SwapResponder2 RESPONDED")
            Unit
        }

        SwapState.Confirmed -> {
            Log.d("SwapKit", "SwapResponder3 INITIATOR_BAILED")
            swapResponderDoer.watchInitiatorBail()
        }

        SwapState.InitiatorBailed -> {
            Log.d("SwapKit", "SwapResponder3 INITIATOR_BAILED")
            swapResponderDoer.bail()
        }

        SwapState.ResponderBailed -> {
            Log.d("SwapKit", "SwapResponder4 RESPONDER_BAILED")
            swapResponderDoer.watchInitiatorRedeem()
        }

        SwapState.InitiatorRedeemed -> {
            Log.d("SwapKit", "SwapResponder5 INITIATOR_REDEEMED")
            swapResponderDoer.redeem()
        }

        SwapState.ResponderRedeemed -> {
            Log.d("SwapKit", "SwapResponder6 RESPONDER_REDEEMED")
            Unit
        }

        SwapState.Refunded -> {
            Log.d("SwapKit", "SwapInitiator7 REFUNDED")
            Unit
        }
    }

    suspend fun refund() = swapResponderDoer.refund()
}

class SwapResponderDoer(
    private val initiatorBlockchain: SwapBlockchain,
    private val responderBlockchain: SwapBlockchain,
    private val swapRepository: SwapRepository,
    val swap: Swap,
) : SwapBailTxListener, SwapRedeemTxListener {
    lateinit var delegate: SwapResponder

    suspend fun watchInitiatorBail() {
        Log.d("SwapKit Responder", "Start watching for initiator bailTx")
        initiatorBlockchain.setBailTxListener(
            this@SwapResponderDoer,
            swap.take.make.redeemAddress,
            swap.secretHash,
            swap.take.refundAddress,
            swap.take.make.refundTime,
            swap.take.makerSafeAmount,
        )
    }

    override suspend fun onBailTransactionSeen(bailTx: String) {
        Log.d("SwapKit Responder", "Initiator bail transaction seen $bailTx")
        val nextSwap = swap.copy(takerSafeTx = bailTx, swapState = SwapState.InitiatorBailed)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    suspend fun bail() {
        val bailTx = responderBlockchain.sendBailTx(
            redeemPKH = swap.take.redeemAddress,
            secretHash = swap.secretHash,
            refundPKH = swap.take.make.refundAddress,
            swap.take.make.refundTime,
            swap.take.makerSafeAmount,
        )
        Log.d("SwapKit Responder", "Sent responder bail tx $bailTx")
        val nextSwap = swap.copy(takerSafeTx = bailTx, swapState = SwapState.ResponderBailed)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    suspend fun watchInitiatorRedeem() {
        Log.d("SwapKit Responder", "Start watching for initiator redeem tx")
        responderBlockchain.setRedeemTxListener(
            this@SwapResponderDoer,
            swap.makerSafeTx!!,
        )
    }

    override suspend fun onRedeemTxSeen(redeemTx: String, secret: String) {
        Log.d("SwapKit", "Initiator redeem tx seen $redeemTx for ${swap.swapState}")
        val nextSwap = swap.copy(
            takerRedeemTx = redeemTx,
            secret = secret,
            swapState = SwapState.InitiatorRedeemed
        )
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    suspend fun redeem() {
        val redeemTx = initiatorBlockchain.sendRedeemTx(
            redeemPKH = swap.take.make.redeemAddress,
            secret = swap.secret!!,
            secretHash = swap.secretHash,
            refundPKH = swap.take.refundAddress,
            refundTime = swap.take.make.refundTime,
            bailTx = swap.takerSafeTx!!,
        )

        Log.d("SwapKit", "Sent responder redeem tx $redeemTx")
        val nextSwap = swap.copy(takerRedeemTx = redeemTx, swapState = SwapState.ResponderRedeemed)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    suspend fun refund() {
        val refundTx = initiatorBlockchain.sendRefundTx(
            redeemPKH = swap.take.redeemAddress,
            secretHash = swap.secretHash,
            refundPKH = swap.take.make.refundAddress,
            refundTime = swap.take.make.refundTime,
            bailTx = swap.makerSafeTx!!
        )
        val nextSwap = swap.copy(takerRefundTx = refundTx, swapState = SwapState.Refunded)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

}