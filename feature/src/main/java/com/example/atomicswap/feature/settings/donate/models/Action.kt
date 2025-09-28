package com.example.atomicswap.feature.settings.donate.models


sealed class Action {
    data object PopBackStack: Action()
}