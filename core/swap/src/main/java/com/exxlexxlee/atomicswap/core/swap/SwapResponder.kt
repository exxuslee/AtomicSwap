package com.exxlexxlee.atomicswap.core.swap

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.exxlexxlee.atomicswap.core.swap.model.BailTx
import com.exxlexxlee.atomicswap.core.swap.model.RedeemTx
import com.exxlexxlee.atomicswap.core.swap.model.Swap

class SwapResponder(
    private val swapResponderDoer: SwapResponderDoer,
    private val isReactive: Boolean,
) {

    suspend fun processNext() = when (swapResponderDoer.state) {
        Swap.State.REQUESTED -> {
            Log.d("SwapKit", "SwapResponder1 REQUESTED")
            Unit
        }

        Swap.State.RESPONDED -> {
            Log.d("SwapKit", "SwapResponder2 RESPONDED")
            swapResponderDoer.watchInitiatorBail()
        }

        Swap.State.INITIATOR_BAILED -> {
            Log.d("SwapKit", "SwapResponder3 INITIATOR_BAILED")
            swapResponderDoer.bail()
        }

        Swap.State.RESPONDER_BAILED -> {
            Log.d("SwapKit", "SwapResponder4 RESPONDER_BAILED")
            if (!isReactive) swapResponderDoer.watchInitiatorRedeem()
            else swapResponderDoer.watchReactRedeem()
        }

        Swap.State.INITIATOR_REDEEMED -> {
            Log.d("SwapKit", "SwapResponder5 INITIATOR_REDEEMED")
            swapResponderDoer.redeem()
        }

        Swap.State.RESPONDER_REDEEMED -> {
            Log.d("SwapKit", "SwapResponder6 RESPONDER_REDEEMED")
            Unit
        }

        Swap.State.REFUNDED -> {
            Log.d("SwapKit", "SwapInitiator7 REFUNDED")
            Unit
        }

        Swap.State.REVEALED -> {
            Log.d("SwapKit", "SwapInitiator8 REVEALED")
            Unit
        }
    }

    suspend fun refund() = swapResponderDoer.refund()
    fun initiatorBailTx() = swapResponderDoer.initiatorBailTx()
    fun responderBailTx() = swapResponderDoer.responderBailTx()
}

class SwapResponderDoer(
    private val initiatorBlockchain: ISwapBlockchain,
    private val responderBlockchain: ISwapBlockchain,
    private val swap: Swap,
) : ISwapBailTxListener, ISwapRedeemTxListener {

    private val mutex = Mutex()
    lateinit var delegate: SwapResponder
    val state get() = swap.state

    suspend fun watchInitiatorBail() {
        Log.d("SwapKit Responder", "Start watching for initiator bailTx")
        initiatorBlockchain.setBailTxListener(
            this@SwapResponderDoer,
            swap.responderRedeemPKH,
            swap.secretHash,
            swap.initiatorRefundPKH,
            swap.initiatorRefundTime,
            swap.initiatorAmount,
        )
    }

    override suspend fun onBailTransactionSeen(bailTx: BailTx) {
        val shouldProcess = mutex.withLock {
            Log.d("SwapKit Responder", "Initiator bail transaction seen $bailTx ${swap.state}")
            if (swap.state != Swap.State.RESPONDED) {
                false
            } else {
                swap.initiatorBailTx = initiatorBlockchain.serializeBailTx(bailTx)
                swap.state = Swap.State.INITIATOR_BAILED
                true
            }
        }

        if (shouldProcess) delegate.processNext()
    }

    suspend fun bail() {
        do {
            try {
                val bail = responderBlockchain.sendBailTx(
                    redeemPKH = swap.initiatorRedeemPKH,
                    secretHash = swap.secretHash,
                    refundPKH = swap.responderRefundPKH,
                    refundTime = swap.responderRefundTime,
                    amount = swap.responderAmount
                )
                swap.state = Swap.State.RESPONDER_BAILED
                Log.d("SwapKit Responder", "Sent responder bail tx $bail")
                swap.responderBailTx = responderBlockchain.serializeBailTx(bail)
            } catch (e: Exception) {
                Log.e("SwapKit Responder", "$e")
                delay(60000)
            } catch (e: Throwable) {
                Log.e("SwapKit Responder", "$e")
                delay(60000)
            }
        } while (swap.state != Swap.State.RESPONDER_BAILED)
        delegate.processNext()
    }

    suspend fun watchInitiatorRedeem() {
        Log.d("SwapKit Responder", "Start watching for initiator redeem tx")
        responderBlockchain.setRedeemTxListener(
            this@SwapResponderDoer,
            responderBlockchain.deserializeBailTx(swap.responderBailTx)!!
        )
    }

    suspend fun watchReactRedeem() {
        Log.d("SwapKit Responder", "Start watching for react redeem tx")
        initiatorBlockchain.setRedeemTxListener(
            this@SwapResponderDoer,
            initiatorBlockchain.deserializeBailTx(swap.initiatorBailTx)!!
        )
    }

    override suspend fun onRedeemTxSeen(redeemTx: RedeemTx) {
        val shouldProcess = mutex.withLock {
            Log.d("SwapKit", "Initiator redeem tx seen $redeemTx for ${swap.state}")

            if (swap.state != Swap.State.RESPONDER_BAILED) {
                false
            } else {
                if (!swap.isReactive) {
                    swap.initiatorRedeemTx = responderBlockchain.serializeRedeemTx(redeemTx)
                    swap.state = Swap.State.INITIATOR_REDEEMED
                } else {
                    swap.responderRedeemTx = redeemTx.txHash
                    swap.state = Swap.State.RESPONDER_REDEEMED
                }
                true
            }
        }

        if (shouldProcess) {
            delegate.processNext() // вызываем уже вне lock
        }
    }

    suspend fun redeem() {
        do {
            try {
                val responderRedeemTx = initiatorBlockchain.sendRedeemTx(
                    swap.responderRedeemPKH,
                    swap.responderRedeemPKId,
                    responderBlockchain.deserializeRedeemTx(swap.initiatorRedeemTx).secret,
                    swap.secretHash,
                    swap.initiatorRefundPKH,
                    swap.initiatorRefundTime,
                    initiatorBlockchain.deserializeBailTx(swap.initiatorBailTx)!!,
                )
                swap.state = Swap.State.RESPONDER_REDEEMED
                swap.responderRedeemTx = responderRedeemTx.txHash
                Log.d("SwapKit", "Sent responder redeem tx $responderRedeemTx")
            } catch (e: Exception) {
                Log.e("SwapKit Responder", "$e")
                delay(60000)
            } catch (e: Throwable) {
                Log.e("SwapKit Responder", "$e")
                delay(60000)
            }
        } while (swap.state != Swap.State.RESPONDER_REDEEMED)
        delegate.processNext()
    }

    suspend fun refund() {
        do {
            try {
                val refundTx = responderBlockchain.sendRefundTx(
                    redeemPKH = swap.initiatorRedeemPKH,
                    secretHash = swap.secretHash,
                    refundPKH = swap.responderRefundPKH,
                    refundTime = swap.responderRefundTime,
                    refundPKId = swap.responderRefundPKId,
                    bailTx = responderBlockchain.deserializeBailTx(swap.responderBailTx)!!
                )
                swap.state = Swap.State.REFUNDED
                swap.refundTx = refundTx.txHash
            } catch (e: Exception) {
                Log.e("SwapKit Responder", "$e")
                delay(60000)
            } catch (e: Throwable) {
                Log.e("SwapKit Responder", "$e")
                delay(60000)
            }
        } while (swap.state != Swap.State.REFUNDED)
    }

    fun initiatorBailTx() = initiatorBlockchain.deserializeBailTx(swap.initiatorBailTx)
    fun responderBailTx() = responderBlockchain.deserializeBailTx(swap.responderBailTx)

}