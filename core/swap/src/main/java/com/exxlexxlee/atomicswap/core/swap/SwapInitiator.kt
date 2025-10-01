package com.exxlexxlee.atomicswap.core.swap

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.exxlexxlee.atomicswap.core.swap.model.BailTx
import com.exxlexxlee.atomicswap.core.swap.model.RedeemTx
import com.exxlexxlee.atomicswap.core.swap.model.Swap

class SwapInitiator(
    private val swapInitiatorDoer: SwapInitiatorDoer,
    private val isReactive: Boolean,
) {

    suspend fun processNext() = when (swapInitiatorDoer.state) {
        Swap.State.REQUESTED -> {
            Log.d("SwapKit", "SwapInitiator1 REQUESTED")
            Unit
        }

        Swap.State.RESPONDED -> {
            Log.d("SwapKit", "SwapInitiator2 RESPONDED")
            swapInitiatorDoer.bail()
        }

        Swap.State.INITIATOR_BAILED -> {
            Log.d("SwapKit", "SwapInitiator3 INITIATOR_BAILED")
            swapInitiatorDoer.watchResponderBail()
        }

        Swap.State.RESPONDER_BAILED -> {
            Log.d("SwapKit", "SwapInitiator4 RESPONDER_BAILED")
            if (!isReactive) swapInitiatorDoer.redeem()
            else swapInitiatorDoer.reveal()
        }

        Swap.State.INITIATOR_REDEEMED -> {
            Log.d("SwapKit", "SwapInitiator5 INITIATOR_REDEEMED")
            Unit
        }

        Swap.State.RESPONDER_REDEEMED -> {
            Log.d("SwapKit", "SwapInitiator6 RESPONDER_REDEEMED")
            Unit
        }

        Swap.State.REFUNDED -> {
            Log.d("SwapKit", "SwapInitiator7 REFUNDED")
            Unit
        }

        Swap.State.REVEALED -> {
            Log.d("SwapKit", "SwapInitiator8 REVEALED")
            swapInitiatorDoer.watchReactRedeem()
        }
    }

    suspend fun refund() = swapInitiatorDoer.refund()
    fun initiatorBailTx() = swapInitiatorDoer.initiatorBailTx()
    fun responderBailTx() = swapInitiatorDoer.responderBailTx()
}

class SwapInitiatorDoer(
    private val initiatorBlockchain: ISwapBlockchain,
    private val responderBlockchain: ISwapBlockchain,
    private val swap: Swap,
) : ISwapBailTxListener, ISwapRedeemTxListener {
    private val mutex = Mutex()
    lateinit var delegate: SwapInitiator
    val state get() = swap.state

    suspend fun bail() {
        do {
            try {
                val bail = initiatorBlockchain.sendBailTx(
                    swap.responderRedeemPKH,
                    swap.secretHash,
                    swap.initiatorRefundPKH,
                    swap.initiatorRefundTime,
                    swap.initiatorAmount
                )
                swap.initiatorBailTx = initiatorBlockchain.serializeBailTx(bail)
                swap.state = Swap.State.INITIATOR_BAILED
                Log.d("SwapKit Initiator", "Sent initiator bail tx $bail")
            } catch (e: Throwable) {
                Log.e("SwapKit Initiator", "$e")
                delay(60000)
            } catch (e: Exception) {
                Log.e("SwapKit Responder", "$e")
                delay(60000)
            }
        } while (swap.state != Swap.State.INITIATOR_BAILED)
        delegate.processNext()
    }


    suspend fun watchResponderBail() {
        responderBlockchain.setBailTxListener(
            this@SwapInitiatorDoer,
            swap.initiatorRedeemPKH,
            swap.secretHash,
            swap.responderRefundPKH,
            swap.responderRefundTime,
            swap.responderAmount,
        )
    }

    override suspend fun onBailTransactionSeen(bailTx: BailTx) {
        val shouldProcess = mutex.withLock {
            Log.d(
                "SwapKit Initiator",
                "Responder bail transaction seen $bailTx for swap with state ${swap.state}"
            )

            if (swap.state != Swap.State.INITIATOR_BAILED) {
                false
            } else {
                val serializeBailTx = responderBlockchain.serializeBailTx(bailTx)
                swap.responderBailTx = serializeBailTx
                swap.state = Swap.State.RESPONDER_BAILED
                true
            }
        }

        if (shouldProcess) {
            delegate.processNext()
        }
    }

    suspend fun redeem() {
        do {
            try {
                val redeemTx = responderBlockchain.sendRedeemTx(
                    redeemPKH = swap.initiatorRedeemPKH,
                    redeemPKId = swap.initiatorRedeemPKId,
                    secret = swap.secret,
                    secretHash = swap.secretHash,
                    refundPKH = swap.responderRefundPKH,
                    refundTime = swap.responderRefundTime,
                    bailTx = responderBlockchain.deserializeBailTx(swap.responderBailTx)!!
                )
                swap.state = Swap.State.INITIATOR_REDEEMED
                swap.initiatorRedeemTx = redeemTx.txHash
                Log.d("SwapKit Initiator", "Sent initiator redeem tx $redeemTx")
            } catch (error: Throwable) {
                Log.e("SwapKit Initiator", "$error")
                delay(60000)
            } catch (e: Exception) {
                Log.e("SwapKit Initiator", "$e")
                delay(60000)
            }
        } while (swap.state != Swap.State.INITIATOR_REDEEMED)
        delegate.processNext()
    }

    suspend fun reveal() {
        do {
            try {
                val revealTx = initiatorBlockchain.sendRevealTx(

                    swapId = initiatorBlockchain.deserializeBailTx(swap.initiatorBailTx)!!
                        .address.hexToByteArray(),
                    chainId = responderBlockchain.chainId,
                    chainContract = responderBlockchain.addressContract,
                    secret = swap.secret,
                    bailTx = responderBlockchain.deserializeBailTx(swap.responderBailTx)!!
                )
                swap.state = Swap.State.REVEALED
                swap.initiatorRevealTx = revealTx.txHash
                Log.d("SwapKit Initiator", "Sent initiator reveal tx $revealTx")
            } catch (error: Throwable) {
                Log.e("SwapKit Initiator", "$error")
                delay(60000)
            } catch (e: Exception) {
                Log.e("SwapKit Initiator", "$e")
                delay(60000)
            }
        } while (swap.state != Swap.State.REVEALED && swap.state != Swap.State.REFUNDED)
        delegate.processNext()
    }

    suspend fun refund() {
        do {
            try {
                val refundTx = initiatorBlockchain.sendRefundTx(
                    redeemPKH = swap.responderRedeemPKH,
                    secretHash = swap.secretHash,
                    refundPKH = swap.initiatorRefundPKH,
                    refundPKId = swap.initiatorRefundPKId,
                    refundTime = swap.initiatorRefundTime,
                    bailTx = initiatorBlockchain.deserializeBailTx(swap.initiatorBailTx)!!
                )
                swap.state = Swap.State.REFUNDED
                swap.refundTx = refundTx.txHash
            } catch (e: Throwable) {
                Log.e("SwapKit Initiator", "$e")
                delay(60000)
            } catch (e: Exception) {
                Log.e("SwapKit Initiator", "$e")
                delay(60000)
            }
        } while (swap.state != Swap.State.REFUNDED)
    }

    fun initiatorBailTx() = initiatorBlockchain.deserializeBailTx(swap.initiatorBailTx)
    fun responderBailTx() = responderBlockchain.deserializeBailTx(swap.responderBailTx)

    override suspend fun onRedeemTxSeen(redeemTx: RedeemTx) {
        swap.state = Swap.State.INITIATOR_REDEEMED
        swap.initiatorRedeemTx = redeemTx.txHash
        Log.d("SwapKit Initiator", "Sent initiator redeem tx $redeemTx")
        delegate.processNext()
    }

    suspend fun watchReactRedeem() {
        Log.d("SwapKit Initiator", "Start watching for reactive redeem tx")
        responderBlockchain.setRedeemTxListener(
            this@SwapInitiatorDoer,
            responderBlockchain.deserializeBailTx(swap.responderBailTx)!!
        )

    }
}
