package com.z1.meutreino.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class Training(
    val name: String,
    val series: Int,
    val repetitions: Int,
    val progress: Int
) : Parcelable

fun Training.isCompleted() = progress == series