package com.exxlexxlee.atomicswap.domain.model

sealed class BadgeType {
    object BadgeDot : BadgeType()
    class BadgeNumber(val number: Int) : BadgeType()
}