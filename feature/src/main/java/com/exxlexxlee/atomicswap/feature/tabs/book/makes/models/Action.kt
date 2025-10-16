package com.exxlexxlee.atomicswap.feature.tabs.book.makes.models


sealed class Action {
    data object TakerToken : Action()
    data object MakerToken : Action()
}


