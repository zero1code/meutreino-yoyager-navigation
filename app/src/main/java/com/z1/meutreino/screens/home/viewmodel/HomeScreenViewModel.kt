package com.z1.meutreino.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.meutreino.model.AppDate
import com.z1.meutreino.model.AppDateState
import com.z1.meutreino.screens.home.screen.HomeScreenUiState
import com.z1.meutreino.screens.home.screen.updateAppDateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeScreenViewModel() : ViewModel() {
    private val calendar = Calendar.getInstance(Locale.getDefault())

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState = _uiState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _uiState.value
    )

    init {
        bindCalendar()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.MonthForward -> monthForward()
            is HomeScreenEvent.MonthBack -> monthBack()
        }
    }

    private fun monthForward() {
        calendar.add(Calendar.MONTH, 1)
        bindCalendar()
    }

    private fun monthBack() {
        calendar.add(Calendar.MONTH, -1)
        bindCalendar()
    }

    private fun bindCalendar() = viewModelScope.launch {
        val currentMonth = calendar.clone() as Calendar
        val maxDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        currentMonth.set(Calendar.DAY_OF_MONTH, 1)
        val daysInMonth = mutableListOf<AppDate>()
        _uiState.update {
            it.copy(
                calendar = calendar,
                currentTime = calendar.time,
                currentDay = calendar.get(Calendar.DAY_OF_MONTH),
                currentMonth = calendar.get(Calendar.MONTH),
                currentYear = calendar.get(Calendar.YEAR)
            )
        }

        for (day in 1..maxDaysInMonth) {
            val nextDay = currentMonth.time
            daysInMonth.add(
                AppDate(
                    date = nextDay,
                    state = _uiState.value.updateAppDateState(nextDay)
                )
            )
            currentMonth.add(Calendar.DAY_OF_MONTH, 1)

        }

        _uiState.update {
            it.copy(
                daysInMonth = daysInMonth.toList()
            )
        }
    }
}