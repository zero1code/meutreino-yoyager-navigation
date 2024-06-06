package com.z1.meutreino.screens.training.viewmodel

sealed class TrainingEvent {
    data object StartTrainingTimer: TrainingEvent()
    data object StopTrainingTimer: TrainingEvent()
    data object StartRestTimer: TrainingEvent()
    data object StopRestTimer: TrainingEvent()
    data class UpdateRestTimer(val timer: Long): TrainingEvent()
}