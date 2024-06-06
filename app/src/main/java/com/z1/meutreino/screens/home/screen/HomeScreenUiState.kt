package com.z1.meutreino.screens.home.screen

import com.z1.meutreino.extension.capitalizeFirst
import com.z1.meutreino.model.AppDate
import com.z1.meutreino.model.AppDateState
import com.z1.meutreino.model.TodayCalendar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class HomeScreenUiState(
    val todayCalendar: TodayCalendar = TodayCalendar(Calendar.getInstance(Locale.getDefault())),
    val calendar: Calendar = Calendar.getInstance(Locale.getDefault()),
    val currentTime: Date = Date(),
    val currentMonth: Int = 0,
    val currentDay: Int = 0,
    val currentYear: Int = 0,
    val daysInMonth: List<AppDate> = emptyList()
)

fun HomeScreenUiState.currentMonthText(): String {
    val sdf = SimpleDateFormat("MMMM", Locale.getDefault())
    val sdf2 = SimpleDateFormat("MMM/yy", Locale.getDefault())
    return if (this.currentYear == this.todayCalendar.year)
        sdf.format(this.currentTime).capitalizeFirst()
    else sdf2.format(this.currentTime).capitalizeFirst()
}

fun HomeScreenUiState.isSameMonth() =
    this.todayCalendar.month == currentMonth && this.todayCalendar.year == currentYear

fun HomeScreenUiState.isFutureDate() = currentTime > todayCalendar.currentTime

fun HomeScreenUiState.getLastDayOfMonth() = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

fun HomeScreenUiState.isWeekend() = todayCalendar.isWeekend()

fun HomeScreenUiState.updateAppDateState(nextDay: Date): AppDateState {
    return when {
        todayCalendar.currentTime < nextDay -> AppDateState.NOT_AVAILABLE
        else -> AppDateState.AVAILABLE
    }
}
