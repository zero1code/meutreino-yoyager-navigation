package com.z1.meutreino.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.z1.meutreino.R
import com.z1.meutreino.extension.LocalTreeTabs
import com.z1.meutreino.ui.components.CustomTopAppBar

@Composable
fun TabContainer(modifier: Modifier = Modifier) {
    val treeTabs = LocalTreeTabs.current
    TabNavigator(HomeTab) {
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CustomTopAppBar(
                    navigationIcon = {
                        if (it.current.options.index.toUInt() != 0u || treeTabs.getNextTabToBack() != null) {
                            IconButton(
                                onClick = {
                                    treeTabs.getNextTabToBack()?.let { tab ->
                                        it.current = tab
                                        treeTabs.removeTab()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    title = {
                        Text(
                            text =
                            if (it.current.options.index.toUInt() == 0u)
                                stringResource(id = R.string.label_calendario)
                            else it.current.options.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                )

            },
            content = { padding ->
                Box(
                    modifier = Modifier.padding(padding)
                ) {
                    CurrentTab()
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    TabNavigationItem(tab = HomeTab)
                    TabNavigationItem(tab = WorkoutTab)
                    TabNavigationItem(tab = ResumeTab)
                }
            }
        )
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val treeTabs = LocalTreeTabs.current
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            indicatorColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            selectedTextColor = MaterialTheme.colorScheme.primary
        ),
        selected = tabNavigator.current.key == tab.key,
        label = { Text(text = tab.options.title) },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        onClick = {
            treeTabs.addTab(tabNavigator.current)
            tabNavigator.current = tab
        },
    )
}