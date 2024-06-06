package com.z1.meutreino.screens.myworkout

import android.util.Log
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.z1.meutreino.extension.LocalTreeTabs
import com.z1.meutreino.extension.findRootNavigator
import com.z1.meutreino.screens.myworkout.screen.MyWorkoutScreen
import com.z1.meutreino.screens.addtraining.AddTrainingContainer

class MyWorkoutContainer(): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val treeTabs = LocalTreeTabs.current

        MyWorkoutScreen(
            onAddWorkoutClick = {
                Log.d("TAG", "Content: ${navigator.parent?.lastItem}")
                navigator.findRootNavigator().push(AddTrainingContainer())
            },
            onBackPressed = {
                treeTabs.getNextTabToBack()?.let {
                    tabNavigator.current = it
                    treeTabs.removeTab()
                }
            }
        )
    }
}