package com.exxlexxlee.atomicswap.core.swap

import com.exxlexxlee.atomicswap.core.swap.model.Swap
import java.math.BigDecimal
import java.security.MessageDigest
import java.util.UUID

class SwapFactory(
) {
    val supportedCoins get() = swapBlockchainCreators.keys

    private val swapBlockchainCreators = mutableMapOf<String, ISwapBlockchainCreator>()

    fun registerSwapBlockchainCreator(coinCode: String, creator: ISwapBlockchainCreator) {
        if (!swapBlockchainCreators.containsKey(coinCode))
            swapBlockchainCreators[coinCode] = creator
    }

    fun clearSwapBlockchains() {
        swapBlockchainCreators.clear()
    }

    private fun createBlockchain(coinCode: String): ISwapBlockchain {
        return swapBlockchainCreators[coinCode]?.create() ?: throw AtomicSwapNotSupported(coinCode)
    }

    fun createAtomicSwapResponder(swap: Swap): SwapResponder {
        val initiatorBlockchain = createBlockchain(swap.initiatorCoinCode)
        val responderBlockchain = createBlockchain(swap.responderCoinCode)
        val swapResponderDoer =
            SwapResponderDoer(initiatorBlockchain, responderBlockchain, swap)
        val swapResponder = SwapResponder(swapResponderDoer, swap.isReactive)
        swapResponderDoer.delegate = swapResponder
        return swapResponder
    }

    fun createAtomicSwapInitiator(swap: Swap): SwapInitiator {
        val initiatorBlockchain = createBlockchain(swap.initiatorCoinCode)
        val responderBlockchain = createBlockchain(swap.responderCoinCode)
        val swapInitiatorDoer =
            SwapInitiatorDoer(initiatorBlockchain, responderBlockchain, swap)
        val swapInitiator = SwapInitiator(swapInitiatorDoer, swap.isReactive)
        swapInitiatorDoer.delegate = swapInitiator
        return swapInitiator
    }

    fun createSwap(
        initiatorCoinCode: String,
        responderCoinCode: String,
        amountFrom: String,
        amountTo: String,
        timeToRefund: Long,
        isReactive: Boolean,
    ): Swap {
        val initiatorBlockchain = createBlockchain(initiatorCoinCode)
        val responderBlockchain = createBlockchain(responderCoinCode)

        val initiatorRedeemPublicKey = responderBlockchain.getRedeemPublicKey()
        val initiatorRefundPublicKey = initiatorBlockchain.getRefundPublicKey()

        val id = UUID.randomUUID().toString()
        val secret = sha256(UUID.randomUUID().toString().toByteArray())

        val swap = Swap().apply {
            this.id = "i-$id"
            this.initiator = true
            this.state = Swap.State.REQUESTED

            this.initiatorCoinCode = initiatorCoinCode
            this.responderCoinCode = responderCoinCode
            this.initiatorAmount = amountFrom
            this.responderAmount = amountTo

            this.initiatorRedeemPKH = initiatorRedeemPublicKey.publicKeyHash
            this.initiatorRedeemPKId = initiatorRedeemPublicKey.publicKeyId
            this.initiatorRefundPKH = initiatorRefundPublicKey.publicKeyHash
            this.initiatorRefundPKId = initiatorRefundPublicKey.publicKeyId

            this.secret = secret
            this.secretHash = sha256(secret)
            this.initiatorRefundTime = timeToRefund
            this.isReactive = isReactive
        }
        return swap
    }

    fun createSwapAsResponder(
        id: String,
        initiatorCoinCode: String,
        responderCoinCode: String,
        amount2: String,
        amount1: String,
        initiatorRefundPKH: ByteArray,
        initiatorRedeemPKH: ByteArray,
        secretHash: ByteArray,
        initiatorRefundTime: Long,
        isReactive: Boolean,
    ): Swap {
        val initiatorBlockchain = createBlockchain(initiatorCoinCode)
        val responderBlockchain = createBlockchain(responderCoinCode)

        val redeemPublicKey = initiatorBlockchain.getRedeemPublicKey()
        val refundPublicKey = responderBlockchain.getRefundPublicKey()

        val swap = Swap().apply {
            this.id = id.replaceFirstChar { 'r' }
            this.initiator = false
            this.state = Swap.State.RESPONDED
            this.initiatorCoinCode = initiatorCoinCode
            this.responderCoinCode = responderCoinCode
            this.responderAmount = amount2
            this.initiatorAmount = amount1
            this.initiatorRedeemPKH = initiatorRedeemPKH
            this.initiatorRefundPKH = initiatorRefundPKH
            this.secretHash = secretHash
            responderRedeemPKH = redeemPublicKey.publicKeyHash
            responderRedeemPKId = redeemPublicKey.publicKeyId
            responderRefundPKH = refundPublicKey.publicKeyHash
            responderRefundPKId = refundPublicKey.publicKeyId
            responderRefundTime = responderBlockchain.tomorrow(initiatorRefundTime)
            this.initiatorRefundTime = initiatorBlockchain.dayAfterTomorrow(initiatorRefundTime)
            this.isReactive = isReactive
        }
        return swap
    }

//    fun retrieveSwapForResponse(
//        id: String,
//        responderRedeemPKH: ByteArray,
//        responderRefundPKH: ByteArray,
//        responderRefundTime: Long,
//        initiatorRefundTime: Long,
//        responderAmount: String,
//    ): Swap {
//        val swapFromDB = db.swapDao.load(id) ?: throw AtomicSwapNotExist(id)
//        swapFromDB.apply {
//            if (this.state == Swap.State.REQUESTED) this.state = Swap.State.RESPONDED
//            this.responderRedeemPKH = responderRedeemPKH
//            this.responderRefundPKH = responderRefundPKH
//            this.responderRefundTime = responderRefundTime
//            this.initiatorRefundTime = initiatorRefundTime
//            this.responderAmount = responderAmount
//        }
//        db.swapDao.save(swapFromDB)
//        return swapFromDB
//    }

    private fun sha256(input: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input)
    }

    fun swapContractAddress(coinCode: String) =
        swapBlockchainCreators[coinCode]?.swapContractAddress()

    fun feeInSafe(coinCode: String): BigDecimal =
        swapBlockchainCreators[coinCode]?.feeInSafe ?: BigDecimal.ZERO

    fun coefficientFee(coinCode: String): BigDecimal =
        swapBlockchainCreators[coinCode]?.coefficientFee ?: BigDecimal.ONE
}

interface ISwapBlockchainCreator {
    fun create(): ISwapBlockchain
    fun swapContractAddress(): String?

    val feeInSafe: BigDecimal
    val coefficientFee: BigDecimal
}

class AtomicSwapNotSupported(coinCode: String) :
    Exception("Atomic swap is not supported for $coinCode")

class AtomicSwapNotExist(id: String) :
    Exception("Atomic swap is not exist id:$id")
