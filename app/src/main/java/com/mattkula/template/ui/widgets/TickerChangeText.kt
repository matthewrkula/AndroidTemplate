package com.mattkula.template.ui.widgets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.mattkula.template.R
import com.mattkula.template.ui.currencyFormat
import com.mattkula.template.ui.percentFormat
import com.mattkula.template.ui.theme.MutedRed
import com.mattkula.template.ui.theme.NeonGreen

@Composable
fun TickerChangeText(
    percentChange: Float = 0.0f,
    currencyChange: Float = 0.0f,
    showPercentage: Boolean = true,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Light,
    modifier: Modifier
) {
    val formattedValue = when {
        showPercentage -> percentFormat.format(percentChange / 100.0f)
        else -> currencyFormat.format(currencyChange)
    }
    Text(
        text = when {
            percentChange >= 0 -> stringResource(R.string.prefix_up_arrow, formattedValue)
            else -> stringResource(R.string.prefix_down_arrow, formattedValue)
        },
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = when {
            percentChange >= 0 -> Color.NeonGreen
            else -> Color.MutedRed
        },
        modifier = modifier
    )
}
