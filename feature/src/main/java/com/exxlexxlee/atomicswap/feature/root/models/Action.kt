package com.exxlexxlee.atomicswap.feature.root.models


sealed class Action {
    data object TakerToken : Action()
    data object MakerToken : Action()
}
