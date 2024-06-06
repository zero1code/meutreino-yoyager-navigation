package com.z1.meutreino.extension

import cafe.adriel.voyager.navigator.Navigator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.capitalizeFirst() =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

fun Navigator.findRootNavigator(): Navigator {
    return if (this.parent == null) {
        this
    } else {
        this.parent!!.findRootNavigator()
    }
}

fun Date.toHumanDate(): String {
    val sdf = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
    return sdf.format(this.time).capitalizeFirst()
}

fun Long.formatTime(): String {
    val time = this
    val minutes = (time % 3600) / 60
    val remainingSeconds = time % 60
    return String.format(Locale.getDefault(),"%02d:%02d", minutes, remainingSeconds)
}

fun Long.formatCountDownTimer(): String {
    var diff = this
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = diff / daysInMilli
    diff %= daysInMilli

    val elapsedHours = diff / hoursInMilli
    diff %= hoursInMilli

    val elapsedMinutes = diff / minutesInMilli
    diff %= minutesInMilli

    val elapsedSeconds = diff / secondsInMilli

    return String.format(Locale.getDefault(),"%02d:%02d", elapsedMinutes, elapsedSeconds)
}

fun Long.formatTimeComplete(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

fun Float?.orZero() = this ?: 0F
fun Int?.orOne() = this ?: 1
