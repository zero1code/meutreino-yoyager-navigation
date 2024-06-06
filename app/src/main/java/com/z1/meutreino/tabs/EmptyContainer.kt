package com.z1.meutreino.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class EmptyContainer : Screen {
    @Composable
    override fun Content() {
        TabContainer()
    }
}