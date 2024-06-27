package com.akimov.wordsfactory.feature.question.presentation

import com.akimov.wordsfactory.common.uiModels.VariantUI
import kotlinx.collections.immutable.ImmutableList

data class QuestionScreenState(
    val question: String,
    val variants: ImmutableList<VariantUI>,
    val progress: Float,
    val isSelectVariantEnabled: Boolean,
)