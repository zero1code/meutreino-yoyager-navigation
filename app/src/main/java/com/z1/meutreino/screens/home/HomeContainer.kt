package com.z1.meutreino.screens.home

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.z1.meutreino.extension.LocalTreeTabs
import com.z1.meutreino.extension.findRootNavigator
import com.z1.meutreino.screens.home.screen.HomeScreen
import com.z1.meutreino.screens.home.viewmodel.HomeScreenViewModel
import com.z1.meutreino.screens.trainingday.TrainingDayContainer
import org.koin.androidx.compose.koinViewModel

class HomeContainer: Screen {

    @Composable
    override fun Content() {
        val viewmodel: HomeScreenViewModel = koinViewModel()
        val uiState = viewmodel.uiState.collectAsStateWithLifecycle(lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current)
        val onEvent = viewmodel::onEvent
        val activity = LocalContext.current as ComponentActivity
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val treeTabs = LocalTreeTabs.current

        HomeScreen(
            uiState = uiState.value,
            onEvent = { newEvent -> onEvent(newEvent) },
            onTrainingDayClick = {
                navigator.findRootNavigator().push(TrainingDayContainer(it))
            },
            onBackPressed = {
                treeTabs.getNextTabToBack()?.let {
                    tabNavigator.current = it
                    treeTabs.removeTab()
                } ?: activity.finish()
            }
        )
    }
}