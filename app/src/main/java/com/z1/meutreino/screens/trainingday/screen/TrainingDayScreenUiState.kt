package com.z1.meutreino.screens.trainingday.screen

import com.z1.meutreino.model.Training

data class TrainingDayScreenUiState(
    val trainingList: List<Training> = emptyList()
)

fun TrainingDayScreenUiState.workoutProgress(): Float {
    val totalPercentage = trainingList.sumOf {
        (it.progress.toDouble() / it.series.toDouble())
    }
    return (totalPercentage / trainingList.size).toFloat()
}