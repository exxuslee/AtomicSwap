package com.exxlexxlee.atomicswap.domain.model

import com.exxlexxlee.atomicswap.domain.model.serrialize.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal


data class Take(
    val takeId: String,
    val make: Make,
    val takerId: String,
    val takerRefundAddress: String,
    val takerRedeemAddress: String,
   val makerFinalAmount: BigDecimal,
  val takerFinalAmount: BigDecimal,
)

