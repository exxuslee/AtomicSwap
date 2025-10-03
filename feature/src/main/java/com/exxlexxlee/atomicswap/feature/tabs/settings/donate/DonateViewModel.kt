package com.exxlexxlee.atomicswap.feature.tabs.settings.donate

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.DonateViewItem
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.ViewState

class DonateViewModel(
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        donates = listOf(
            DonateViewItem(
                "Bitcoin",
                "36b5Z19fLrbgEcV1dwhwiFjix86bGweXKC",
                R.drawable.outline_currency_bitcoin_24
            ),
            DonateViewItem(
                "Ethereum",
                "0x6F1C4B2bd0489e32AF741C405CcA696E8a95ce9C",
                R.drawable.outline_currency_bitcoin_24
            ),
            DonateViewItem(
                "BSC",
                "0x6F1C4B2bd0489e32AF741C405CcA696E8a95ce9C",
                R.drawable.outline_currency_bitcoin_24
            ),
            DonateViewItem(
                "Solana",
                "2zMufqDhhiMbcQRVLiAVrBv9SWdHvxrHgAsdQfMbUaJS",
                R.drawable.outline_currency_bitcoin_24
            ),
            DonateViewItem(
                "Tron",
                "TKQMJN2aFAyPwaFCdg3AxhRT9xqsRuTvb3",
                R.drawable.outline_currency_bitcoin_24
            ),
        )
    )
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.OnAmountSelected -> viewState =
                viewState.copy(selectedAmount = viewEvent.amount)

            is Event.OnTokenSelected -> viewState = viewState.copy(selectedChain = viewEvent.pos)

            is Event.OnTickerSelected -> viewState = viewState.copy(selectedTicker = viewEvent.pos)

            Event.AddressCopied -> viewState = viewState.copy(isAddressCopied = true)
        }
    }

}