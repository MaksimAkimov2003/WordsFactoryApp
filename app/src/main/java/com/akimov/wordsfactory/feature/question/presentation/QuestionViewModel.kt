package com.akimov.wordsfactory.feature.question.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.training.interactor.GetQuestionForWordInteractor
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.domain.training.useCase.timer.ObserveTimerUseCase
import com.akimov.wordsfactory.common.extensions.indexToLetter
import com.akimov.wordsfactory.common.uiModels.HighlightType
import com.akimov.wordsfactory.common.uiModels.VariantUI
import com.akimov.wordsfactory.navigation.question.WORD_ARG
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private const val SECONDS_AMOUNT = 5

class QuestionViewModel(
    observeTimerUseCase: ObserveTimerUseCase,
    getQuestionForWordInteractor: GetQuestionForWordInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val word: WordTrainingDto =
        Json.decodeFromString(checkNotNull(savedStateHandle.get<String>(WORD_ARG)) { "Word must be passed" })
    private val question = viewModelScope.async {
        getQuestionForWordInteractor(word = word)
    }

    private val progress: Flow<Float> = observeTimerUseCase(
        durationInSeconds = SECONDS_AMOUNT + 1,
        withMillis = true
    ).map {
        (it as Double).toFloat() / SECONDS_AMOUNT
    }
    private val selectedVariantIndex: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val isAnimationFinished = MutableStateFlow(false)

    private val variantsInitial: Deferred<ImmutableList<VariantUI>> =
        viewModelScope.async {
            question.await().variants.mapIndexed { index, variantString ->
                VariantUI(
                    letter = index.indexToLetter(),
                    text = variantString,
                    highlightType = HighlightType.DEFAULT
                )
            }.toImmutableList()
        }

    private var hasNavigatedNext = false

    val state: StateFlow<QuestionScreenState> =
        combine(
            progress,
            selectedVariantIndex,
            isAnimationFinished,
        ) { progress: Float, selectedIndex: Int?, isAnimationFinished ->

            val variants: ImmutableList<VariantUI> =
                variantsInitial.await().mapIndexed { index, variantUI ->
                    if (index == selectedIndex) {
                        variantUI.copy(
                            highlightType = calculateHighlightType(
                                selectedIndex
                            )
                        )
                    } else {
                        variantUI
                    }
                }.toImmutableList()

            if ((progress == 0f) || (isAnimationFinished)) {
                val isAnswerCorrect =
                    variants.any { variantUI -> variantUI.highlightType == HighlightType.CORRECT }
                sendNavigateNextEffect(isAnswerCorrect)
            }

            QuestionScreenState(
                question = question.await().tittle,
                progress = progress,
                isSelectVariantEnabled = (selectedIndex == null),
                variants = variants
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            QuestionScreenState(
                question = "",
                progress = 0f,
                isSelectVariantEnabled = true,
                variants = persistentListOf()
            )
        )

    private fun sendNavigateNextEffect(isAnswerCorrect: Boolean) {
        if (hasNavigatedNext) return
        viewModelScope.launch {
            hasNavigatedNext = true
            _effects.emit(QuestionScreenEffect.NavigateNext(isAnswerCorrect = isAnswerCorrect))
        }
    }

    private val _effects = MutableSharedFlow<QuestionScreenEffect>()
    val effects = _effects.asSharedFlow()

    init {
        Log.d("QuestionViewModel", word.name)
    }

    fun onVariantClick(index: Int) {
        selectedVariantIndex.update { index }
    }

    fun onAnimationFinished() {
        isAnimationFinished.update { true }
    }

    private suspend fun calculateHighlightType(selectedIndex: Int) =
        if (question.await().correctVariantIndex == selectedIndex) HighlightType.CORRECT else HighlightType.WRONG
}
