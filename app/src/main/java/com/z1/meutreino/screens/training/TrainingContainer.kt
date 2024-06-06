package com.z1.meutreino.screens.training

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.z1.meutreino.model.Training
import com.z1.meutreino.screens.training.screen.TrainingScreen
import com.z1.meutreino.screens.training.viewmodel.TrainingViewModel
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel

@Parcelize
data class TrainingContainer(val training: Training) : Screen, Parcelable {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val currentTraining = remember { training }
        val viewModel: TrainingViewModel = koinViewModel()
        val uiState =
            viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
        val onEvent = viewModel::onEvent

        if (uiState.value.training == null) {
            viewModel.setTraining(currentTraining)
        }

        TrainingScreen(
            uiState = uiState.value,
            onEvent = { newEvent -> onEvent(newEvent) },
            onBackPressed = {
                navigator.pop()
            }
        )
    }
}