package com.exxlexxlee.atomicswap.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Swap @OptIn(ExperimentalUuidApi::class) constructor(
	val id: Uuid,
	val type: SwapType,
	val amount: Double,
	val asset: String,
	val timestamp: Long
)

enum class SwapType { TAKER, MAKER }
