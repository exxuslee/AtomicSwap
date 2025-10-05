package com.exxlexxlee.atomicswap.core.swap.domain.usecase

import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapBlockchain
import com.exxlexxlee.atomicswap.core.swap.domain.blockchain.SwapBlockchainCreator
import com.exxlexxlee.atomicswap.core.swap.domain.repository.SwapRepository
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.core.swap.model.SwapState
import com.exxlexxlee.atomicswap.core.swap.model.Take
import java.security.MessageDigest
import java.util.UUID

class SwapFactory(
    private val swapRepository: SwapRepository,
) {
    private val swapBlockchainCreators = mutableMapOf<Blockchain, SwapBlockchainCreator>()

    fun registerSwapBlockchainCreator(blockchain: Blockchain, creator: SwapBlockchainCreator) {
        if (!swapBlockchainCreators.containsKey(blockchain))
            swapBlockchainCreators[blockchain] = creator
    }

    fun clearSwapBlockchains() {
        swapBlockchainCreators.clear()
    }

    private fun createBlockchain(coinCode: Blockchain): SwapBlockchain {
        return swapBlockchainCreators[coinCode]?.create()
            ?: throw Exception("AtomicSwapNotSupported: $coinCode")
    }

    fun createResponder(swap: Swap): SwapResponder {
        val initiatorBlockchain = createBlockchain(swap.take.make.takerToken.blockchain)
        val responderBlockchain = createBlockchain(swap.take.make.makerToken.blockchain)
        val swapResponderDoer =
            SwapResponderDoer(initiatorBlockchain, responderBlockchain, swapRepository, swap)
        val swapResponder = SwapResponder(swapResponderDoer)
        swapResponderDoer.delegate = swapResponder
        return swapResponder
    }

    fun createInitiator(swap: Swap): SwapInitiator {
        val initiatorBlockchain = createBlockchain(swap.take.make.takerToken.blockchain)
        val responderBlockchain = createBlockchain(swap.take.make.makerToken.blockchain)
        val swapInitiatorDoer =
            SwapInitiatorDoer(initiatorBlockchain, responderBlockchain, swapRepository, swap)
        val swapInitiator = SwapInitiator(swapInitiatorDoer)
        swapInitiatorDoer.delegate = swapInitiator
        return swapInitiator
    }

    fun createSwap(take: Take): Swap {
        val id = UUID.randomUUID().toString()
        val secret = sha256(UUID.randomUUID().toString().toByteArray())
        val secretHash = sha256(secret).toHexString()

        val swap = Swap(
            swapId = id,
            take = take,
            timestamp = System.currentTimeMillis(),
            swapState = SwapState.Requested,
            isRead = true,
            secret = secret.toHexString(),
            secretHash = secretHash,
        )
        return swap
    }

    private fun sha256(input: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input)
    }
}