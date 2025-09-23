package com.example.atomicswap.domain.model

data class Swap(
	val id: Long,
	val type: SwapType,
	val amount: Double,
	val asset: String,
	val timestamp: Long
)

enum class SwapType { TAKER, MAKER }
