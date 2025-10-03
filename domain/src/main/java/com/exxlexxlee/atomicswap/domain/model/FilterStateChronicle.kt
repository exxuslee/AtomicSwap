package com.exxlexxlee.atomicswap.domain.model

import com.exxlexxlee.atomicswap.domain.R

sealed class FilterStateChronicle(
    val icon: Int,
    val pos: Int,
    val relatedStates: List<SwapState>
) {
    data object MyMake : FilterStateChronicle(
        R.drawable.outline_note_stack_24,
        0,
        listOf(SwapState.Requested)
    )

    data object Active : FilterStateChronicle(
        R.drawable.outline_gavel_24,
        1,
        listOf(
            SwapState.Responded,
            SwapState.InitiatorBailed,
            SwapState.ResponderBailed
        )
    )

    data object Complete : FilterStateChronicle(
        R.drawable.outline_handshake_24,
        2,
        listOf(
            SwapState.InitiatorRedeemed,
            SwapState.ResponderRedeemed
        )
    )

    data object Refund : FilterStateChronicle(
        R.drawable.outline_undo_24,
        3,
        listOf(SwapState.Refunded)
    )

    companion object {
        val values = listOf(MyMake, Active, Complete, Refund)

        fun fromPos(pos: Int): FilterStateChronicle =
            values.getOrNull(pos) ?: Active
    }
}