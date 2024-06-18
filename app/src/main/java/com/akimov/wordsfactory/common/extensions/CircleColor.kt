package com.akimov.wordsfactory.common.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.akimov.wordsfactory.common.theme.success
import com.akimov.wordsfactory.common.theme.yellow
import com.akimov.wordsfactory.screens.training.presentation.CircleColor

@Composable
fun CircleColor.mapToCompose() =
    when (this) {
        CircleColor.Blue -> MaterialTheme.colorScheme.secondary
        CircleColor.Green -> MaterialTheme.colorScheme.success
        CircleColor.Yellow -> yellow
        CircleColor.Red -> MaterialTheme.colorScheme.error
        CircleColor.Orange -> MaterialTheme.colorScheme.primary
    }