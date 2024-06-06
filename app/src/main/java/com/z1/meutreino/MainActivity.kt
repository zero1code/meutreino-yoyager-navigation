package com.z1.meutreino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.z1.meutreino.extension.LocalTreeTabs
import com.z1.meutreino.extension.TreeTabs
import com.z1.meutreino.tabs.EmptyContainer
import com.z1.meutreino.ui.theme.MeuTreinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeuTreinoTheme {
                CompositionLocalProvider(LocalTreeTabs provides TreeTabs()) {
                    Navigator(screen = EmptyContainer()) {
                        FadeTransition(
                            navigator = it
                        )
                    }
                }
            }
        }
    }
}

