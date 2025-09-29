package com.exxlexxlee.atomicswap.feature.settings.notification.models


sealed class Action {
    data object PopBackStack: Action()
}