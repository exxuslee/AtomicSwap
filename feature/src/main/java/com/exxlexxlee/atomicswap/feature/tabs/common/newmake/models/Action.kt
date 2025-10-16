package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models


sealed class Action {
    data object TakerToken : Action()
    data object MakerToken : Action()
}