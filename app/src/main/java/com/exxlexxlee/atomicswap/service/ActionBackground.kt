package com.exxlexxlee.atomicswap.service

sealed class ActionBackground(val id: String) {
    data object Start : ActionBackground("com.exxlexxlee.atomicswap.ACTION_START")
    data object Stop : ActionBackground("com.exxlexxlee.atomicswap.ACTION_STOP")

    companion object {
        fun fromId(id: String): ActionBackground? {
            return when (id) {
                Start.id -> Start
                Stop.id -> Stop
                else -> throw Exception("Unknown ActionBackground id")
            }
        }
    }

}