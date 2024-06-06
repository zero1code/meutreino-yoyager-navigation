package com.z1.meutreino.di

import com.z1.meutreino.screens.home.viewmodel.HomeScreenViewModel
import com.z1.meutreino.screens.training.viewmodel.TrainingViewModel
import com.z1.meutreino.screens.trainingday.viewmodel.TrainingDayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideHomeScreenViewModel = module {
    viewModel { HomeScreenViewModel() }
}

private val provideTrainingDayViewModel = module {
    viewModel { TrainingDayViewModel() }
}

private val provideTrainingViewModel = module {
    viewModel { TrainingViewModel() }
}

val appModule = listOf(
    provideHomeScreenViewModel,
    provideTrainingDayViewModel,
    provideTrainingViewModel
)