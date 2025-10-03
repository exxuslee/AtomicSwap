package com.exxlexxlee.atomicswap.feature.tabs.settings.donate

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.DonateChainItem
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.DonateTickerItem
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.models.ViewState

class DonateViewModel(
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        donates = DonateChainItem.chains(),
        tickers = DonateTickerItem.trxList(),
        selectedTicker = DonateTickerItem.Usdt,
        selectedChain = DonateChainItem.Tron,
    )
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.OnAmountSelected -> viewState =
                viewState.copy(selectedAmount = viewEvent.amount)

            is Event.OnChainSelected -> chainSelected(viewEvent.chainItem)

            is Event.OnTickerSelected -> viewState =
                viewState.copy(selectedTicker = viewEvent.tickerItem)

            Event.AddressCopied -> viewState = viewState.copy(isAddressCopied = true)
        }
    }

    private fun chainSelected(chain: DonateChainItem) {
        viewState = viewState.copy(selectedChain = chain)

        when (chain) {
            DonateChainItem.BSC -> viewState = viewState.copy(
                tickers = listOf(
                    DonateTickerItem.Bnb, DonateTickerItem.Usdt, DonateTickerItem.Usdc,
                ),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
            )

            DonateChainItem.Bitcoin -> viewState = viewState.copy(
                tickers = DonateTickerItem.btcList(),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Bitcoin,
            )

            DonateChainItem.Ethereum -> viewState = viewState.copy(
                tickers = DonateTickerItem.ethList(),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
            )

            DonateChainItem.Solana -> viewState = viewState.copy(
                tickers = DonateTickerItem.solList(),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
            )

            DonateChainItem.Tron -> viewState = viewState.copy(
                tickers = DonateTickerItem.trxList(),
                selectedChain = chain,
                selectedTicker = DonateTickerItem.Usdt,
            )
        }

    }

}