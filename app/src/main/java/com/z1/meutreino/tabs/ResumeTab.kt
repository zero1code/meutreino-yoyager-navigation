package com.z1.meutreino.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.z1.meutreino.screens.resume.ResumeContainer

object ResumeTab: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Resumo"
            val icon = rememberVectorPainter(image = Icons.Rounded.Analytics)
            
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = ResumeContainer())
    }
}