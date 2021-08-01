package com.mattkula.template.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.mattkula.template.ui.theme.MutedRed

@Composable
@Preview(showBackground = true, heightDp = 100)
private fun LineChartPreview() {
    LineChart(
        listOf(3f, 10f, 4f, 5f, 8f, -20f, 13f),
        modifier = Modifier.padding(8.dp),
        strokeColor = Color.MutedRed
    )
}

@Composable
fun LineChart(
    points: List<Float>,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Blue,
) {
    var canvasWidth by rememberSaveable { mutableStateOf(0.0f) }
    var canvasHeight by rememberSaveable { mutableStateOf(0.0f) }

    val (path, lineY) = rememberScaledPath(points, canvasWidth, canvasHeight)

    val outputPath = remember { Path() }
    val pathMeasure = remember { PathMeasure() }

    var animationFinished by rememberSaveable { mutableStateOf(false) }
    val percentDrawn = remember { Animatable(if (animationFinished) 1.0f else 0.0f) }

    val pathToDraw = pathMeasure.rememberPathToDraw(path, outputPath, percentDrawn.value)

    LaunchedEffect(Unit) {
        val state = percentDrawn.animateTo(
            1.0f,
            animationSpec = tween(durationMillis = 1000)
        )
        animationFinished = state.endReason == AnimationEndReason.Finished
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        canvasWidth = size.width
        canvasHeight = size.height

        drawLine(
            color = Color.LightGray,
            start = Offset(x = 0f, y = lineY),
            end = Offset(x = canvasWidth, y = lineY),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)),
        )

        drawPath(
            path = pathToDraw,
            color = strokeColor,
            style = Stroke(
                width = 6.dp.value,
                cap = StrokeCap.Round,
            ),
        )
    }
}

@Composable
private fun rememberScaledPath(
    points: List<Float>,
    width: Float,
    height: Float
) = remember(points, width, height) {
    val path = Path()
    val min = points.minOrNull() ?: Float.MIN_VALUE
    val max = points.maxOrNull() ?: Float.MAX_VALUE

    val adjustedPoints = points.map { (it - min) / (max - min) }

    val widthDiff = width / (adjustedPoints.size - 1)

    adjustedPoints.forEachIndexed { index, number ->
        val x = widthDiff * index
        val y = height - (number * height)

        when (index) {
            0 -> path.moveTo(x, y)
            else -> path.lineTo(x, y)
        }
    }

    val lineY = height - (adjustedPoints.first() * height)

    path to lineY
}

@Composable
private fun PathMeasure.rememberPathToDraw(
    inputPath: Path,
    outputPath: Path,
    drawPercent: Float
) = remember(inputPath, drawPercent) {
    setPath(inputPath, false)
    getSegment(
        startDistance = 0f,
        stopDistance = this.length * drawPercent,
        destination = outputPath,
        startWithMoveTo = true
    )
     outputPath
}
