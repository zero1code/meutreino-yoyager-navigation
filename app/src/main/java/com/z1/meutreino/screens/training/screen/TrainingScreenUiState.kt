package com.z1.meutreino.screens.training.screen

import androidx.compose.runtime.Immutable
import com.z1.meutreino.extension.formatCountDownTimer
import com.z1.meutreino.extension.formatTime
import com.z1.meutreino.extension.orZero
import com.z1.meutreino.model.Training
import java.util.concurrent.TimeUnit

@Immutable
data class TrainingScreenUiState(
    val training: Training? = null,
    val trainingTimer: Long = 0L,
    val isTraining: Boolean = false,
    val isFirstOpen: Boolean = true,
    val restTimer: Long = TimeUnit.SECONDS.toMillis(5),
    val selectedRestTimer: Long = TimeUnit.SECONDS.toMillis(5),
    val isResting: Boolean = false
)

fun TrainingScreenUiState.getCurrentSeries() =
    training?.let { "${it.progress}/${it.series}" }.orEmpty()

fun TrainingScreenUiState.currentTimer() = when {
    isFirstOpen -> (0L).formatTime()
    standBy() -> (0L).formatTime()
    isTraining -> trainingTimer.formatTime()
    else -> restTimer.formatCountDownTimer()
}

fun TrainingScreenUiState.seriesProgress() =
    training?.let {
        (it.progress.toDouble() / it.series.toDouble()).toFloat()
    }.orZero()

fun TrainingScreenUiState.standBy() = isFirstOpen || (isTraining.not() && isResting.not())

fun TrainingScreenUiState.countDownTimerProgress() =
    if(isResting) (restTimer.toDouble() / selectedRestTimer.toDouble()).toFloat() else 1F

