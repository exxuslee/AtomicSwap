package com.example.atomicswap.domain.model

import android.content.Context
import java.util.Calendar
import java.util.Date

data class Notification(
    val id: Long = System.currentTimeMillis(),
    val showInForeground: Boolean,
    val isRead: Boolean,
    val title: String,
    val body: String,
    val groupCode: Int,
)