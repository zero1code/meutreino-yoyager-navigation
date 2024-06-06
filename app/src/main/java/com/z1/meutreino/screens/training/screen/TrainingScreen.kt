package com.z1.meutreino.screens.training.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.z1.meutreino.data.trainingList
import com.z1.meutreino.model.isCompleted
import com.z1.meutreino.screens.training.viewmodel.TrainingEvent
import com.z1.meutreino.ui.components.CustomTopAppBar
import com.z1.meutreino.ui.theme.CelticBlue
import com.z1.meutreino.ui.theme.MediumSeaGreen
import com.z1.meutreino.ui.theme.MeuTreinoTheme
import java.util.concurrent.TimeUnit

data class Chip(
    val name: String,
    val timer: Long,
)

@Composable
fun TrainingScreen(
    modifier: Modifier = Modifier,
    uiState: TrainingScreenUiState,
    onEvent: (TrainingEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Header(
                title = uiState.training?.name.orEmpty(),
                onBackPressed = onBackPressed
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    text = "Descanço"
                )
                RestTimerSelector(
                    onChipClick = {
                        onEvent(TrainingEvent.UpdateRestTimer(it))
                    }
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Timer(
                    modifier = Modifier
                        .padding(bottom = 64.dp),
                    currentTime = uiState.currentTimer(),
                    message = when {
                        uiState.standBy() -> "Começar"
                        uiState.isTraining -> "Treinando"
                        else -> "Descançando"
                    },
                    currentProgress = uiState.countDownTimerProgress()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Series(
                        currentSeries = uiState.getCurrentSeries(),
                        seriesProgress = uiState.seriesProgress()
                    )
                    Repetitions(
                        repetitions = uiState.training?.repetitions.toString()
                    )
                }
            }

            uiState.training?.takeIf { it.isCompleted() }
                ?.run {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MediumSeaGreen
                        ),
                        onClick = onBackPressed
                    ) {
                        Text(
                            text = "Treino concluído",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } ?: run {
                ButtonsOptions(
                    isTraining = uiState.isTraining,
                    isResting = uiState.isResting,
                    isFirstOpen = uiState.isFirstOpen,
                    standBy = uiState.standBy(),
                    onStartClick = {
                        onEvent(TrainingEvent.StartTrainingTimer)
                    },
                    onFinishedClick = {
                        onEvent(TrainingEvent.StopTrainingTimer)
                    }
                )
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
fun Timer(
    modifier: Modifier = Modifier,
    currentTime: String,
    message: String,
    currentProgress: Float,
) {
    val config = LocalConfiguration.current
    val cardSize = config.screenWidthDp.dp / 2
    CustomCircularProgress(
        modifier = modifier,
        title = currentTime,
        titleFontSize = 56.sp,
        message = message,
        messageFontSize = 24.sp,
        size = cardSize,
        strokeWidth = 8.dp,
        progressColor = MediumSeaGreen,
        currentProgress = currentProgress
    )
}

@Composable
fun Series(
    modifier: Modifier = Modifier,
    currentSeries: String,
    seriesProgress: Float,
) {
    val config = LocalConfiguration.current
    val cardSize = config.screenWidthDp.dp / 4
    CustomCircularProgress(
        modifier = modifier,
        title = currentSeries,
        titleFontSize = 24.sp,
        message = "Séries",
        messageFontSize = 16.sp,
        size = cardSize,
        strokeWidth = 2.dp,
        progressColor = MediumSeaGreen,
        currentProgress = seriesProgress
    )
}

@Composable
fun Repetitions(
    modifier: Modifier = Modifier,
    repetitions: String,
) {
    val config = LocalConfiguration.current
    val cardSize = config.screenWidthDp.dp / 4
    CustomCircularProgress(
        modifier = modifier,
        title = repetitions,
        titleFontSize = 24.sp,
        message = "Repetições",
        messageFontSize = 12.sp,
        size = cardSize,
        strokeWidth = 2.dp,
        progressColor = CelticBlue
    )
}

@Composable
fun RestTimerSelector(
    modifier: Modifier = Modifier,
    onChipClick: (Long) -> Unit,
) {
    val chipList = remember {
        mutableListOf(
            Chip("5s", TimeUnit.SECONDS.toMillis(5)),
            Chip("45s", TimeUnit.SECONDS.toMillis(45)),
            Chip("1min", TimeUnit.MINUTES.toMillis(1)),
        )
    }
    var selected by remember {
        mutableStateOf(chipList[0])
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        chipList.forEach { chip ->
            FilterChip(
                modifier = Modifier
                    .padding(end = 8.dp),
                selected = selected == chip,
                onClick = {
                    if (selected != chip) onChipClick(chip.timer)
                    selected = chip
                },
                shape = MaterialTheme.shapes.small,
                colors = FilterChipDefaults.filterChipColors(
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    selectedContainerColor = MaterialTheme.colorScheme.primary
                ),
                label = { Text(text = chip.name) }
            )
        }
    }
}

@Composable
fun ButtonsOptions(
    modifier: Modifier = Modifier,
    isTraining: Boolean,
    isResting: Boolean,
    isFirstOpen: Boolean,
    standBy: Boolean,
    onStartClick: () -> Unit,
    onFinishedClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .padding(end = 8.dp),
            shape = MaterialTheme.shapes.large,
            enabled = isResting.not() && standBy.not(),
            onClick = { if (isResting.not()) onFinishedClick() }
        ) {
            Text(
                text = "Feito",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = MaterialTheme.shapes.large,
            enabled = isTraining.not() || isFirstOpen,
            onClick = { if (isTraining.not()) onStartClick() }
        ) {
            Text(
                text = "Iniciar",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun CustomCircularProgress(
    modifier: Modifier = Modifier,
    title: String,
    titleFontSize: TextUnit,
    messageFontSize: TextUnit,
    message: String,
    size: Dp,
    strokeWidth: Dp,
    progressColor: Color,
    currentProgress: Float = 1F,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(size),
            shape = RoundedCornerShape(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            onClick = { }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = titleFontSize,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = messageFontSize
                )
            }
        }
        Progress(
            modifier = Modifier
                .size(size),
            currentProgress = currentProgress,
            strokeWidth = strokeWidth,
            color = progressColor
        )
    }
}

@Composable
fun Progress(
    modifier: Modifier = Modifier,
    strokeWidth: Dp,
    color: Color,
    currentProgress: Float,
) {
    val animateProgress by animateFloatAsState(
        targetValue = currentProgress,
        animationSpec = tween(500, 0, FastOutSlowInEasing),
        label = "animation-progress"
    )
    if (currentProgress == 0F) {
        CircularProgressIndicator(
            modifier = modifier,
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            color = color
        )
    } else {
        CircularProgressIndicator(
            modifier = modifier,
            progress = { animateProgress },
            strokeWidth = strokeWidth,
            strokeCap = StrokeCap.Round,
            trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            color = color
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MeuTreinoTheme {
        TrainingScreen(
            uiState = TrainingScreenUiState(training = trainingList[1]),
            onEvent = {},
            onBackPressed = {}
        )
    }
}