package com.z1.meutreino.screens.trainingday.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.meutreino.data.trainingList
import com.z1.meutreino.screens.trainingday.screen.TrainingDayScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrainingDayViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(TrainingDayScreenUiState())
    val uiState = _uiState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _uiState.value
    )

    init {
        getTrainingDay()
    }

    private fun getTrainingDay() =
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    trainingList = trainingList
                )
            }
        }
}