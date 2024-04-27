package com.akimov.wordsfactory.common.components.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.common.extensions.drawLinearIndicator
import com.akimov.wordsfactory.common.extensions.drawLinearIndicatorTrack
import com.akimov.wordsfactory.common.theme.ink

@Composable
fun AppLinearProgressIndicator(
    progress: Float,
    color: Brush,
    modifier: Modifier = Modifier,
    trackColor: Color = ink,
    strokeCap: StrokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
) {
    val coercedProgress = progress.coerceIn(0f, 1f)
    Canvas(
        modifier
            .progressSemantics(coercedProgress)
            .size(width = 240.dp, height = 5.0.dp)
    ) {
        val strokeWidth = size.height
        drawLinearIndicatorTrack(trackColor, strokeWidth, strokeCap)
        drawLinearIndicator(0f, coercedProgress, color, strokeWidth, strokeCap)
    }
}