package com.exxlexxlee.atomicswap.feature.settings.donate.models


sealed class Action {
    data object PopBackStack: Action()
}