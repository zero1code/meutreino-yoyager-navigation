@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.z1.meutreino.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.z1.meutreino.ui.theme.MeuTreinoTheme

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
    navigationIcon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    actions: @Composable (RowScope.() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        ),
        navigationIcon = {
            navigationIcon?.let {
                navigationIcon()
            }
        },
        title = {
            title()
        },
        actions = {
            actions?.let { it() }
        }
    )
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun PreviewDestinationBar() {
    MeuTreinoTheme {
        CustomTopAppBar(
            title = {},
            actions = {}
        )
    }
}