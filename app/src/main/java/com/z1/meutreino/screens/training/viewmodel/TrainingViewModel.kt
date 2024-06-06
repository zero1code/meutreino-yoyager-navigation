package com.z1.meutreino.screens.training.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.z1.meutreino.model.Training
import com.z1.meutreino.screens.training.screen.TrainingScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TrainingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TrainingScreenUiState())
    val uiState = _uiState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _uiState.value
    )

    private var timerJob: Job? = null

    fun onEvent(event: TrainingEvent) {
        when (event) {
            TrainingEvent.StartTrainingTimer -> startTrainingTimer()
            TrainingEvent.StopTrainingTimer -> stopTrainingTimer()
            TrainingEvent.StartRestTimer -> {}
            TrainingEvent.StopRestTimer -> {}
            is TrainingEvent.UpdateRestTimer -> updateRestTimer(event.timer)
        }
    }

    fun setTraining(training: Training) = viewModelScope
        .launch {
            _uiState.update {
                it.copy(training = training)
            }
        }

    private fun startTrainingTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isTraining = true,
                    isResting = false,
                    isFirstOpen = false,
                    restTimer = 0L
                )
            }

            timerFlow(TimeUnit.SECONDS.toMillis(1))
                .takeWhile { timerJob != null }
                .collect {
                    _uiState.update {
                        it.copy(
                            trainingTimer = it.trainingTimer.plus(1)
                        )
                    }
                }
        }
    }

    private fun stopTrainingTimer() = viewModelScope
        .launch {
            timerJob?.cancel()
            timerJob = null
            _uiState.update {
                it.copy(
                    trainingTimer = TimeUnit.SECONDS.toMillis(0),
                    isTraining = false,
                    isResting = true,
                    training = it.training?.copy(progress = it.training.progress.plus(1))
                )
            }
            startRestTimer()
        }

    private fun startRestTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    restTimer = it.selectedRestTimer
                )
            }
            timerFlow(TimeUnit.SECONDS.toMillis(1))
                .takeWhile { timerJob != null }
                .collect {
                    _uiState.update {
                        val restTimer = it.restTimer - TimeUnit.SECONDS.toMillis(1)
                        it.copy(
                            restTimer = restTimer,
                            isResting = restTimer > 0L
                        )
                    }
                    if (_uiState.value.restTimer == 0L) stopTimer()
                }
        }
    }

    private fun stopTimer() = viewModelScope
        .launch {
            timerJob?.cancel()
            timerJob = null
            _uiState.update {
                it.copy(
                    restTimer = it.selectedRestTimer,
                    isResting = false,
                    isTraining = false
                )
            }
        }

    private fun updateRestTimer(newTimer: Long) = viewModelScope
        .launch {
            _uiState.update {
                it.copy(
                    restTimer = newTimer,
                    selectedRestTimer = newTimer
                )
            }
        }


    private fun timerFlow(intervalMillis: Long): Flow<Unit> = flow {
        while (true) {
            delay(intervalMillis)
            emit(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}