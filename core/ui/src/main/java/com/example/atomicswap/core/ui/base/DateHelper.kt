package com.example.atomicswap.core.ui.base

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.atomicswap.core.ui.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DateHelper : KoinComponent {

    private val context: Context by inject()

    private fun getTimeFormat(): String {
        val is24HourFormat = DateFormat.is24HourFormat(context)
        return if (is24HourFormat) "HH:mm" else "h:mm a"
    }

    fun getDayAndTime(date: Date): String = formatDate(date, "MMM d, ${getTimeFormat()}")
    fun getOnlyTime(date: Date): String = formatDate(date, getTimeFormat())
    fun getFullDate(date: Date): String = formatDate(date, "MMM d, yyyy, ${getTimeFormat()}")

    fun getTxDurationString(context: Context, durationInSec: Long): String {
        return when {
            durationInSec < 10 -> context.getString(R.string.Duration_instant)
            durationInSec < 60 -> context.getString(R.string.Duration_Seconds, durationInSec)
            durationInSec < 60 * 60 -> {
                val minutes = durationInSec / 60
                context.getString(R.string.Duration_Minutes, minutes)
            }
            else -> {
                val hours = durationInSec / (60 * 60)
                context.getString(R.string.Duration_Hours, hours)
            }
        }
    }

    fun getTxDurationIntervalString(context: Context, durationInSec: Long): String {
        return when {
            durationInSec < 10 -> context.getString(R.string.Duration_instant)
            durationInSec < 60 -> {
                val seconds = context.getString(R.string.Duration_Seconds, durationInSec)
                context.getString(R.string.Duration_Within, seconds)
            }
            durationInSec < 60 * 60 -> {
                val minutes = context.getString(R.string.Duration_Minutes, durationInSec / 60)
                context.getString(R.string.Duration_Within, minutes)
            }
            else -> {
                val hours = context.getString(R.string.Duration_Hours, durationInSec / (60 * 60))
                context.getString(R.string.Duration_Within, hours)
            }
        }
    }

    fun shortDate(date: Date, near: String = "MMM d", far: String = "MMM dd, yyyy"): String = if (isThisYear(date)) {
        formatDate(date, near)
    } else {
        formatDate(date, far)
    }

    fun formatDate(date: Date, pattern: String): String {
        return SimpleDateFormat(
            DateFormat.getBestDateTimePattern(Locale.getDefault(), pattern),
            Locale.getDefault()
        ).format(date)
    }

    fun getSecondsAgo(dateInMillis: Long): Long {
        val differenceInMillis = Date().time - dateInMillis
        return differenceInMillis / 1000
    }

    fun isSameDay(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance().apply { time = date1 }
        val calendar2 = Calendar.getInstance().apply { time = date2 }

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(
            Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }

    private fun isThisYear(date: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance().apply { time = date }

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
    }

    fun formatTime(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date(time)
        return getOnlyTime( calendar.time,)
    }

    fun formatDate(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date(date)

        val today = Calendar.getInstance()
        if (calendar[Calendar.YEAR] == today[Calendar.YEAR] && calendar[Calendar.DAY_OF_YEAR] == today[Calendar.DAY_OF_YEAR]) {
            return context.getString(R.string.Timestamp_Today)
        }

        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)
        if (calendar[Calendar.YEAR] == yesterday[Calendar.YEAR] && calendar[Calendar.DAY_OF_YEAR] == yesterday[Calendar.DAY_OF_YEAR]) {
            return context.getString(R.string.Timestamp_Yesterday)
        }

        return shortDate(calendar.time, "MMMM d", "MMMM d, yyyy")
    }
}