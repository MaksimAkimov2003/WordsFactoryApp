package com.akimov.wordsfactory.screens.question

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.training.model.QuestionDto
import com.akimov.wordsfactory.common.extensions.indexToLetter
import com.akimov.wordsfactory.common.uiModels.HighlightType
import com.akimov.wordsfactory.common.uiModels.VariantUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SECONDS_AMOUNT = 5

private const val ONE_SECOND = 1000L

class QuestionViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val question =
        savedStateHandle.get<QuestionDto>("question") ?: error("Question is not provided")

    private val progress: Flow<Float> = flow {
        repeat(SECONDS_AMOUNT) {
            delay(ONE_SECOND)
            emit((it + 1) / 5f)
        }
    }.flowOn(Dispatchers.Default)
    private val selectedVariantIndex: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val variantsInitial: ImmutableList<VariantUI> =
        question.variants.mapIndexed { index, variantString ->
            VariantUI(
                letter = index.indexToLetter(),
                text = variantString,
                highlightType = HighlightType.DEFAULT
            )
        }.toImmutableList()

    val state: StateFlow<QuestionScreenState> =
        combine(progress, selectedVariantIndex) { progress: Float, selectedIndex: Int? ->
            if (progress == 1f) viewModelScope.launch { _effects.emit(QuestionScreenEffect.NavigateNext) }

            QuestionScreenState(
                question = question.tittle,
                progress = progress,
                isSelectVariantEnabled = (selectedIndex == null),
                variants = variantsInitial.mapIndexed { index, variantUI ->
                    if (index == selectedIndex) variantUI.copy(
                        highlightType = calculateHighlightType(
                            selectedIndex
                        )
                    )
                    else variantUI
                }.toImmutableList()
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            QuestionScreenState(
                question = question.tittle,
                progress = 0f,
                isSelectVariantEnabled = true,
                variants = variantsInitial
            )
        )

    private val _effects = MutableSharedFlow<QuestionScreenEffect>()
    val effects = _effects.asSharedFlow()

    fun onVariantClick(index: Int) {
        selectedVariantIndex.update { index }
    }

    private fun calculateHighlightType(selectedIndex: Int) =
        if (question.correctVariantIndex == selectedIndex) HighlightType.CORRECT else HighlightType.WRONG
}