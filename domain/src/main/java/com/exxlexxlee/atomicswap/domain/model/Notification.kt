package com.exxlexxlee.atomicswap.domain.model

data class Notification(
    val id: Long = System.currentTimeMillis(),
    val showInForeground: Boolean,
    val isRead: Boolean,
    val title: String,
    val body: String,
    val groupCode: Int,
)