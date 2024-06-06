package com.z1.meutreino.screens.myworkout.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z1.meutreino.data.trainingList
import com.z1.meutreino.model.Training

@Composable
fun MyWorkoutScreen(
    modifier: Modifier = Modifier,
    onAddWorkoutClick: () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = onAddWorkoutClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    end = padding.calculateEndPadding(LayoutDirection.Ltr),
                )
        ) {
//            EmptyData(
//                modifier = Modifier
//                    .fillMaxSize(),
//                message = "Nenhum treino cadastrado"
//            )

            repeat(3) {
                WorkoutCard(
                    trainingList = trainingList
                )
            }
        }
    }

    BackHandler(enabled = true) {
        onBackPressed()
    }
}

@Composable
fun WorkoutCard(
    modifier: Modifier = Modifier,
    trainingList: List<Training>,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        onClick = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    bottom = 16.dp,
                    end = 16.dp,
                    top = 8.dp
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier,
                    text = "Treino A",
                    fontSize = 24.sp,
                )

                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    shape = MaterialTheme.shapes.small,
                    onClick = { /*TODO*/ },
                    label = { Text(text = "Seg") }
                )
                AssistChip(
                    shape = MaterialTheme.shapes.small,
                    onClick = { /*TODO*/ },
                    label = { Text(text = "Qua") }
                )
                AssistChip(
                    shape = MaterialTheme.shapes.small,
                    onClick = { /*TODO*/ },
                    label = { Text(text = "Sex") }
                )
            }

            trainingList.forEach {
                Column() {
                    Text(text = it.name)

                    Row {
                        Text(text = "${it.series}x séries de ")
                        Text(text = "${it.repetitions}x repetições")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}