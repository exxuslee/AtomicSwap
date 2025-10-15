package com.exxlexxlee.atomicswap.feature.tabs.book.make.models


sealed class Action {
    data object TakerToken : Action()
    data object MakerToken : Action()
}


