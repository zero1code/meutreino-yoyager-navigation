package com.z1.meutreino.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.z1.meutreino.screens.myworkout.MyWorkoutContainer

object WorkoutTab: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Meus treinos"
            val icon = rememberVectorPainter(image = Icons.Rounded.FitnessCenter)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = MyWorkoutContainer())
    }
}