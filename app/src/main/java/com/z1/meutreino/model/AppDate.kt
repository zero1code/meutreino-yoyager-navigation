package com.z1.meutreino.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.z1.meutreino.extension.capitalizeFirst
import com.z1.meutreino.ui.theme.CoralRed
import com.z1.meutreino.ui.theme.MediumSeaGreen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class AppDateState {
    AVAILABLE,
    NOT_AVAILABLE,
    DONE,
    NOT_DONE;

    val backgroundColor: Color
    @Composable
    @ReadOnlyComposable
    get() = when(this) {
        AVAILABLE -> MaterialTheme.colorScheme.background
        NOT_AVAILABLE -> MaterialTheme.colorScheme.background
        DONE -> MediumSeaGreen
        NOT_DONE -> CoralRed
    }

    val textColor: Color
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        AVAILABLE -> MaterialTheme.colorScheme.onBackground
        NOT_AVAILABLE -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
        DONE -> MaterialTheme.colorScheme.onPrimary
        NOT_DONE -> MaterialTheme.colorScheme.onError
    }

    val borderColor: Color
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        NOT_AVAILABLE -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        else -> MaterialTheme.colorScheme.primary
    }
}

data class AppDate(
    val date: Date,
    var state: AppDateState = AppDateState.AVAILABLE
)

fun AppDate.weekDay(): String {
    val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
    return sdf.format(this.date).capitalizeFirst()
}

fun AppDate.day(): String {
    val cal = Calendar.getInstance()
    cal.time = this.date
    return cal[Calendar.DAY_OF_MONTH].toString()
}
