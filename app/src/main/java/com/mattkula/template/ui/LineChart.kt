package com.mattkula.template.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true, heightDp = 100)
private fun LineChartPreview() {
    LineChart(
        listOf(3f, 10f, 4f, 5f, 8f, -20f, 13f),
        modifier = Modifier.padding(8.dp),
        strokeColor = Color.Red
    )
}

@Composable
fun LineChart(
    points: List<Float>,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Blue,
) {
    val path = remember { Path() }
    val outputPath = remember { Path() }
    val pathMeasure = remember { PathMeasure() }

    var animationFinished by rememberSaveable { mutableStateOf(false) }
    val percentDrawn = remember { Animatable(if (animationFinished) 1.0f else 0.0f) }

    LaunchedEffect(Unit) {
        val state = percentDrawn.animateTo(
            1.0f,
            animationSpec = tween(durationMillis = 1000)
        )
        animationFinished = state.endReason == AnimationEndReason.Finished
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        if (points.isNotEmpty()) {
            val min = points.minOrNull() ?: Float.MIN_VALUE
            val max = points.maxOrNull() ?: Float.MAX_VALUE

            val adjustedPoints = points.map { (it - min) / (max - min) }
            val widthDiff = canvasWidth / (adjustedPoints.size - 1)

            path.reset()
            adjustedPoints.forEachIndexed { index, number ->
                val x = widthDiff * index
                val y = canvasHeight - (number * canvasHeight)

                when (index) {
                    0 -> path.moveTo(x, y)
                    else -> path.lineTo(x, y)
                }
            }

            pathMeasure.apply {
                setPath(path, false)
                getSegment(
                    startDistance = 0f,
                    stopDistance = pathMeasure.length * percentDrawn.value,
                    destination = outputPath,
                    startWithMoveTo = true
                )
            }

            val lineY = canvasHeight - (adjustedPoints.first() * canvasHeight)
            drawLine(
                color = Color.LightGray,
                start = Offset(x = 0f, y = lineY),
                end = Offset(x = canvasWidth, y = lineY),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )

            drawPath(
                path = outputPath,
                color = strokeColor,
                style = Stroke(
                    width = 6.dp.value,
                    cap = StrokeCap.Round,
                ),
            )
        }
    }
}
