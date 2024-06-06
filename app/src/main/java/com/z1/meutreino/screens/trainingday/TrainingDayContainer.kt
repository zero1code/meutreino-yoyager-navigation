package com.z1.meutreino.screens.trainingday

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.z1.meutreino.screens.training.TrainingContainer
import com.z1.meutreino.screens.trainingday.screen.TrainingDayScreen
import com.z1.meutreino.screens.trainingday.viewmodel.TrainingDayViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@Serializable
data class TrainingDayContainer(@Contextual val currentDate: Date) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: TrainingDayViewModel = koinViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
        TrainingDayScreen(
            modifier = Modifier
                .fillMaxSize(),
            uiState = uiState.value,
            currentDate = currentDate,
            onTrainingClick = {
                navigator.push(TrainingContainer(it))
            },
            onBackPressed = {
                navigator.pop()
            }
        )
    }
}