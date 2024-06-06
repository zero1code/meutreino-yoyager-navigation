package com.z1.meutreino.screens.resume

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.z1.meutreino.extension.LocalTreeTabs
import com.z1.meutreino.screens.resume.screen.ResumeScreen

class ResumeContainer : Screen {
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val treeTabs = LocalTreeTabs.current
        val navigator = LocalNavigator.currentOrThrow
        ResumeScreen(
            modifier = Modifier
                .fillMaxSize(),
            onBackPressed = {
                treeTabs.getNextTabToBack()?.let {
                    tabNavigator.current = it
                    treeTabs.removeTab()
                }
            }
        )
    }
}