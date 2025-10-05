package com.exxlexxlee.atomicswap.core.swap.domain.usecase

import android.util.Log
import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapBailTxListener
import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapBlockchain
import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapRedeemTxListener
import com.exxlexxlee.atomicswap.core.swap.domain.repository.SwapRepository
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.core.swap.model.SwapState

class SwapInitiator(
    private val swapInitiatorDoer: SwapInitiatorDoer,
) {

    suspend fun processNext(swapState: SwapState) = when (swapState) {
        SwapState.Requested -> {
            Log.d("SwapKit", "SwapInitiator1 REQUESTED")
            Unit
        }

        SwapState.Responded -> {
            Log.d("SwapKit", "SwapInitiator2 RESPONDED")
            Unit
        }

        SwapState.Confirmed -> {
            Log.d("SwapKit", "SwapInitiator3 CONFIRMED")
            swapInitiatorDoer.bail()
        }

        SwapState.InitiatorBailed -> {
            Log.d("SwapKit", "SwapInitiator4 INITIATOR_BAILED")
            swapInitiatorDoer.watchResponderBail()
        }

        SwapState.ResponderBailed -> {
            Log.d("SwapKit", "SwapInitiator5 RESPONDER_BAILED")
            swapInitiatorDoer.redeem()
        }

        SwapState.InitiatorRedeemed -> {
            Log.d("SwapKit", "SwapInitiator6 INITIATOR_REDEEMED")
            swapInitiatorDoer.watchResponderRedeem()
        }

        SwapState.ResponderRedeemed -> {
            Log.d("SwapKit", "SwapInitiator7 RESPONDER_REDEEMED")
            Unit
        }

        SwapState.Refunded -> {
            Log.d("SwapKit", "SwapInitiator8 REFUNDED")
            Unit
        }

    }

    suspend fun refund() = swapInitiatorDoer.refund()
}

class SwapInitiatorDoer(
    private val initiatorBlockchain: SwapBlockchain,
    private val responderBlockchain: SwapBlockchain,
    private val swapRepository: SwapRepository,
    val swap: Swap,
) : SwapBailTxListener, SwapRedeemTxListener {
    lateinit var delegate: SwapInitiator

    suspend fun bail() {
        val bailTx = initiatorBlockchain.sendBailTx(
            swap.take.redeemAddress,
            swap.secretHash,
            swap.take.make.refundAddress,
            swap.take.make.refundTime,
            swap.take.takerSafeAmount,
        )
        Log.d("SwapKit Initiator", "Sent initiator bail tx $bailTx")
        val nextSwap = swap.copy(takerSafeTx = bailTx, swapState = SwapState.InitiatorBailed)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    suspend fun watchResponderBail() {
        responderBlockchain.setBailTxListener(
            this@SwapInitiatorDoer,
            swap.take.redeemAddress,
            swap.secretHash,
            swap.take.make.refundAddress,
            swap.take.make.refundTime,
            swap.take.takerSafeAmount,
        )
    }

    suspend fun watchResponderRedeem() {
        initiatorBlockchain.setRedeemTxListener(
            this@SwapInitiatorDoer,
            swap.takerSafeTx!!,
        )
    }

    override suspend fun onBailTransactionSeen(bailTx: String) {
        Log.d("SwapKit Initiator", "Responder bail transaction seen $bailTx for swap with state")
        val nextSwap = swap.copy(makerSafeTx = bailTx, swapState = SwapState.ResponderBailed)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    suspend fun redeem() {
        val redeemTx = responderBlockchain.sendRedeemTx(
            redeemPKH = swap.take.redeemAddress,
            secret = swap.secret!!,
            secretHash = swap.secretHash,
            refundPKH = swap.take.make.refundAddress,
            refundTime = swap.take.make.refundTime,
            bailTx = swap.makerSafeTx!!
        )
        Log.d("SwapKit Initiator", "Sent initiator redeem tx $redeemTx")
        val nextSwap = swap.copy(takerRedeemTx = redeemTx, swapState = SwapState.InitiatorRedeemed)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    suspend fun refund() {
        val refundTx = initiatorBlockchain.sendRefundTx(
            redeemPKH = swap.take.make.redeemAddress,
            secretHash = swap.secretHash,
            refundPKH = swap.take.refundAddress,
            refundTime = swap.take.make.refundTime,
            bailTx = swap.takerSafeTx!!
        )
        val nextSwap = swap.copy(takerRefundTx = refundTx, swapState = SwapState.Refunded)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

    override suspend fun onRedeemTxSeen(redeemTx: String, secret: String) {
        Log.d("SwapKit Initiator", "Sent initiator redeem tx $redeemTx")
        val nextSwap = swap.copy(makerRedeemTx = redeemTx, swapState = SwapState.ResponderRedeemed)
        swapRepository.updateSafe(nextSwap)
        delegate.processNext(nextSwap.swapState)
    }

}
