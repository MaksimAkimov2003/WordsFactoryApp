package com.akimov.wordsfactory.feature.trainingFinished.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.training.useCase.words.GetWordsForTrainingUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrainingFinishedViewModel(
    private val getWordsForTrainingUseCase: GetWordsForTrainingUseCase
) : ViewModel() {
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _effect: MutableSharedFlow<TrainingFinishedEffect> = MutableSharedFlow()
    val effect = _effect.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    fun onAgainClicked() {
        viewModelScope.launch {
            _isLoading.update { true }
            val wordsForTraining = getWordsForTrainingUseCase()
            _effect.emit(TrainingFinishedEffect.StartTrainingAgain(wordsForTraining))
            _isLoading.update { false }
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _effect.emit(TrainingFinishedEffect.NavigateBack)
        }
    }
}