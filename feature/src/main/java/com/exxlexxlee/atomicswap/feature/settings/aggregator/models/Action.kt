package com.exxlexxlee.atomicswap.feature.settings.aggregator.models

sealed class Action {
    data object PopBackStack : Action()

}