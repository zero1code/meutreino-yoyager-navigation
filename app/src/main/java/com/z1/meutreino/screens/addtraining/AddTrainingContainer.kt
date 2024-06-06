package com.z1.meutreino.screens.addtraining

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.z1.meutreino.screens.addtraining.screen.AddTrainingScreen

class AddTrainingContainer : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AddTrainingScreen(
            modifier = Modifier
                .fillMaxSize(),
            onBackPressed = {
                navigator.pop()
            }
        )
    }
}