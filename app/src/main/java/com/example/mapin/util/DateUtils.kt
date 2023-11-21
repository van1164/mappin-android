package com.example.mapin.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun formatDateTime(dateTimeString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())

        return try {
            val date = inputFormat.parse(dateTimeString)
            formatDateOrTime(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            "Invalid Date"
        }
    }

    private fun formatDateOrTime(date: Date): String {
        val currentDate = Date()
        val diffInMillis = currentDate.time - date.time
        val diffInHours = diffInMillis / (60 * 60 * 1000)

        return if (diffInHours < 24) {
            // 같은 날짜인 경우
            "$diffInHours hours ago"
        } else {
            // 다른 날짜인 경우
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            outputFormat.format(date)
        }
    }
}
