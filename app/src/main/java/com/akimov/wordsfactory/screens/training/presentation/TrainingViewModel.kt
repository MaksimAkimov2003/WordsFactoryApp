package com.akimov.wordsfactory.screens.training.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.dictionary.useCase.GetWordsInDictionaryCountUseCase
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.domain.training.useCase.timer.ObserveTimerUseCase
import com.akimov.domain.training.useCase.words.GetWordsForTrainingUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TIMER_DURATION_SECONDS = 5

class TrainingViewModel(
    private val getWordsInDictionaryCountUseCase: GetWordsInDictionaryCountUseCase,
    private val getWordsForTrainingUseCase: GetWordsForTrainingUseCase,
    observeTimerUseCase: ObserveTimerUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<TrainingScreenState> =
        MutableStateFlow(TrainingScreenState.NoWords)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TrainingScreenEffect>()
    val effect = _effect.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    private var wordsForTraining: List<WordTrainingDto>? = null

    private val timer =
        observeTimerUseCase(durationInSeconds = TIMER_DURATION_SECONDS)
            .onEach { second ->
                _state.update { currentState ->
                    TrainingScreenState.EnoughWords(
                        wordsCount = (currentState as TrainingScreenState.EnoughWords).wordsCount,
                        trainingState =
                        TrainingState.Started(
                            secondsToStart = second,
                        ),
                    )
                }
                if (second == 0) {
                    delay(timeMillis = 1000L)
                    _effect.emit(
                        TrainingScreenEffect.NavigateNext(
                            wordsForTraining = checkNotNull(wordsForTraining) {
                                "Words for training are not initialized"
                            }
                        )
                    )
                }
            }

    init {
        viewModelScope.launch {
            val wordsCount = getWordsInDictionaryCountUseCase()
            if (wordsCount == 0) {
                _state.update { TrainingScreenState.NoWords }
            } else {
                _state.update {
                    TrainingScreenState.EnoughWords(
                        wordsCount = wordsCount,
                        trainingState = TrainingState.NotStarted,
                    )
                }
            }
        }
    }

    fun onStartTraining() {
        viewModelScope.launch {
            timer.collect()
        }
        viewModelScope.launch {
            wordsForTraining = getWordsForTrainingUseCase()
        }
    }
}
