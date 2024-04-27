package com.akimov.wordsfactory.common.uiModels

import androidx.compose.runtime.Stable

@Stable
data class VariantUI(
    val letter: String,
    val text: String,
    val highlightType: HighlightType
)
