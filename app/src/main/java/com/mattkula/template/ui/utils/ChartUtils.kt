package com.mattkula.template.ui.utils

import androidx.compose.ui.graphics.Color
import com.mattkula.template.ui.theme.MutedRed
import com.mattkula.template.ui.theme.NeonGreen

fun List<Float>.chartColor(): Color = when {
    first() > last() -> Color.MutedRed
    else -> Color.NeonGreen
}
