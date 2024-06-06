package com.z1.meutreino.screens.trainingday.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.NavigateBefore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.OnBackPressed
import com.z1.meutreino.data.trainingList
import com.z1.meutreino.extension.toHumanDate
import com.z1.meutreino.model.Training
import com.z1.meutreino.ui.components.CustomTopAppBar
import com.z1.meutreino.ui.theme.MediumSeaGreen
import com.z1.meutreino.ui.theme.MeuTreinoTheme
import java.util.Date

@Composable
fun TrainingDayScreen(
    modifier: Modifier = Modifier,
    uiState: TrainingDayScreenUiState,
    currentDate: Date,
    onTrainingClick: (Training) -> Unit,
    onBackPressed: () -> Unit
) {
    val listState = rememberLazyListState()
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Header(
                title = currentDate.toHumanDate(),
                onBackPressed = onBackPressed
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
        ) {
            WorkoutProgress(
                currentProgress = uiState.workoutProgress()
            )
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                items(uiState.trainingList) { item ->
                    TrainingCard(
                        training = item,
                        onTrainingClick = { onTrainingClick(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit
) {
    CustomTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    )
}

@Composable
fun WorkoutProgress(
    modifier: Modifier = Modifier,
    currentProgress: Float,
) {
    val animateProgress by animateFloatAsState(
        targetValue = currentProgress,
        animationSpec = tween(500, 500, FastOutSlowInEasing),
        label = "animation-progress"
    )
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth(),
        progress = { animateProgress },
        trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
        color = MediumSeaGreen
    )
}

@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    training: Training,
    onTrainingClick: (Training) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
        onClick = { onTrainingClick(training) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(text = training.name)

                Row {
                    Text(text = "${training.series}x séries de ")
                    Text(text = "${training.repetitions}x repetições")
                }
            }

            TrainingProgress(
                maxProgress = training.series,
                currentProgress = training.progress
            )
        }
    }
}

@Composable
fun TrainingProgress(
    modifier: Modifier = Modifier,
    maxProgress: Int,
    currentProgress: Int,
) {
    val progress by remember { mutableFloatStateOf((currentProgress / maxProgress.toFloat())) }
    val animateProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, 500, FastOutSlowInEasing),
        label = "animation-progress"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { animateProgress },
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            color = MediumSeaGreen
        )

        Text(
            text = "${(progress * 100).toInt()}%",
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MeuTreinoTheme {
        TrainingDayScreen(
            uiState = TrainingDayScreenUiState(trainingList = trainingList),
            currentDate = Date(),
            onTrainingClick = {},
            onBackPressed = {}
        )
    }
}