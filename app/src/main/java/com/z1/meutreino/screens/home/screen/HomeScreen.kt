@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.z1.meutreino.screens.home.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.OnBackPressed
import com.z1.meutreino.model.AppDate
import com.z1.meutreino.model.AppDateState
import com.z1.meutreino.model.day
import com.z1.meutreino.model.weekDay
import com.z1.meutreino.screens.home.viewmodel.HomeScreenEvent
import com.z1.meutreino.ui.theme.MeuTreinoTheme
import com.z1.meutreino.ui.theme.OrangePeel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeScreenUiState,
    onEvent: (HomeScreenEvent) -> Unit,
    onTrainingDayClick: (Date) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val config = LocalConfiguration.current
    val horizontalPadding = config.screenWidthDp.dp / 4

    LaunchedEffect(key1 = uiState.currentMonth, key2 = uiState.currentYear) {
        val scrollTo = when {
            uiState.isSameMonth() -> uiState.currentDay
            uiState.isFutureDate() -> 1
            else -> uiState.getLastDayOfMonth()
        } - 1
        listState.animateScrollToItem(scrollTo, 50)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MonthSelector(
            modifier = Modifier
                .weight(1f),
            currentMonth = uiState.currentMonthText(),
            onMonthFoward = { onEvent(HomeScreenEvent.MonthForward) },
            onMonthBack = { onEvent(HomeScreenEvent.MonthBack) }
        )

        LazyRow(
            modifier = Modifier
                .weight(9f)
                .wrapContentHeight(),
            state = listState,
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        ) {
            itemsIndexed(
                items = uiState.daysInMonth,
                key = { _, appDate -> appDate.day() }
            ) { _, item ->
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CardDay(
                        appDate = item,
                        onDayClick = {
                            if (it.state == AppDateState.NOT_AVAILABLE) {
                                Toast.makeText(context, "Treino indisponível", Toast.LENGTH_SHORT).show()
                            } else {
                                onTrainingDayClick(it.date)
                            }
                        }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                if (uiState.isWeekend()) OrangePeel
                else MaterialTheme.colorScheme.primary
            ),
            shape = MaterialTheme.shapes.large,
            onClick = {
                scope.launch {
                    listState.animateScrollToItem(uiState.currentDay - 1, 50)
                    delay(300)
                    onTrainingDayClick(uiState.currentTime)

                }
            }
        ) {
            Text(
                text =
                if (uiState.isWeekend()) "Descançar né meu fi"
                else "Começar o treino de hoje",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    BackHandler(enabled = true) {
        onBackPressed()
    }
}

@Composable
fun CardDay(
    modifier: Modifier = Modifier,
    appDate: AppDate,
    onDayClick: (AppDate) -> Unit
) {
    val config = LocalConfiguration.current
    val cardPadding = config.screenWidthDp.dp / 16
    val cardSize = config.screenWidthDp.dp / 2
    Card(
        modifier = modifier
            .padding(horizontal = cardPadding)
            .size(cardSize),
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(4.dp, appDate.state.borderColor),
        colors = CardDefaults.cardColors(
            containerColor = appDate.state.backgroundColor
        ),
        onClick = { onDayClick(appDate) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = appDate.day(),
                fontSize = 64.sp,
                color = appDate.state.textColor
            )
            Text(
                text = appDate.weekDay(),
                color = appDate.state.textColor
            )
        }
    }
}

@Composable
fun MonthSelector(
    modifier: Modifier = Modifier,
    currentMonth: String,
    onMonthFoward: () -> Unit,
    onMonthBack: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onMonthBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier.width(200.dp),
            text = currentMonth,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        IconButton(onClick = onMonthFoward) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MeuTreinoTheme {
        HomeScreen(
            uiState = HomeScreenUiState(
                daysInMonth = listOf(
                    AppDate(date = Date()),
                    AppDate(date = Date()),
                    AppDate(date = Date()),

                    )
            ),
            onEvent = {},
            onTrainingDayClick = {},
            onBackPressed = {}
        )
    }
}