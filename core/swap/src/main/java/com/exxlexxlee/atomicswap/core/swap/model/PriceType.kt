package com.exxlexxlee.atomicswap.core.swap.model

sealed class PriceType {
    data object Fixed : PriceType()
    data object Market : PriceType()
}