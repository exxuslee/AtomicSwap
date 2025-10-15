package com.exxlexxlee.atomicswap.domain.model

import com.exxlexxlee.atomicswap.domain.R

sealed class FilterStateBook(
    val icon: Int,
    val pos: Int,
) {

    data object Make : FilterStateBook(
        R.drawable.outline_gavel_24,
        0,
    )

    data object MyMake : FilterStateBook(
        R.drawable.outline_note_stack_24,
        1,
    )

    data object Subscription : FilterStateBook(
        R.drawable.outline_notifications_active_24,
        2,
    )

    companion object {
        val values = listOf(Make, MyMake, Subscription)

        fun fromPos(pos: Int): FilterStateBook =
            values.getOrNull(pos) ?: Make
    }
}