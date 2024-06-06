package com.z1.meutreino.model

import java.util.Calendar
import java.util.Date

class TodayCalendar(
    calendar: Calendar
) {
    val currentTime: Date = calendar.time
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH)
    val year = calendar.get(Calendar.YEAR)

    private val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    fun isWeekend() = dayOfWeek == 1 || dayOfWeek == 7

}