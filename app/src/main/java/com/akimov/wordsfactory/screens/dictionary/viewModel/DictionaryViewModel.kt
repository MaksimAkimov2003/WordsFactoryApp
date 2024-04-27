package com.akimov.wordsfactory.screens.dictionary.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akimov.domain.dictionary.model.WordInfoDto
import com.akimov.domain.dictionary.useCase.SaveWordToDictionaryUseCase
import com.akimov.domain.dictionary.useCase.SearchWordUseCase
import com.akimov.domain.dictionary.useCase.StartListenAudioUseCase
import com.akimov.domain.dictionary.useCase.StopListenAudioUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DictionaryViewModel(
    private val searchWordUseCase: SearchWordUseCase,
    private val saveWordUseCase: SaveWordToDictionaryUseCase,
    private val startListenAudioUseCase: StartListenAudioUseCase,
    private val stopListenAudioUseCase: StopListenAudioUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(
        DictionaryScreenState(
            query = "",
            contentState = ContentState.NotInput
        )
    )

    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<DictionaryScreenEffect>()
    val effects = _effects.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000L))

    fun onAddToDictionaryClicked(word: WordInfoDto) {
        viewModelScope.launch {
            _state.update { screenState ->
                val contentState = screenState.contentState as? ContentState.Content
                    ?: throw IllegalStateException("State is not Content, when clicking to Add to dictionary button")

                screenState.copy(
                    contentState = contentState.copy(
                        wordSavingState = WordSavingState.Saving
                    )
                )
            }
            val wordSavingResult = saveWordUseCase(word = word)

            _state.update { screenState ->
                val contentState = screenState.contentState as? ContentState.Content
                    ?: throw IllegalStateException("State is not Content, when saving word to dictionary")

                screenState.copy(
                    contentState = contentState.copy(
                        wordSavingState = WordSavingState.NotSaving
                    )
                )
            }

            wordSavingResult.onFailure {
                _effects.emit(
                    DictionaryScreenEffect.ShowSnackbar(
                        message = "Failed to save word to dictionary",
                        isSuccess = false
                    )
                )
            }

            wordSavingResult.onSuccess {
                _effects.emit(
                    DictionaryScreenEffect.ShowSnackbar(
                        message = "Word saved to dictionary",
                        isSuccess = true
                    )
                )
            }
        }
    }

    fun onSearchClicked(query: String) {
        // Set loading
        _state.update { screenState ->
            screenState.copy(
                query = query,
                contentState = ContentState.Loading
            )
        }

        viewModelScope.launch {
            _effects.emit(DictionaryScreenEffect.HideKeyboard)
        }
        viewModelScope.launch {

            val searchResult = searchWordUseCase(query = query)

            searchResult.onFailure {
                _state.update { screenState ->
                    screenState.copy(
                        contentState = ContentState.NotResults
                    )
                }
                _effects.emit(
                    DictionaryScreenEffect.ShowSnackbar(
                        message = it.message ?: "Failed to search word",
                        isSuccess = false
                    )
                )
            }

            searchResult.onSuccess { wordInfo ->
                _state.update { screenState ->
                    screenState.copy(
                        contentState = ContentState.Content(
                            wordInfo = wordInfo,
                            audioState = AudioState.NotPlaying,
                            wordSavingState = WordSavingState.NotSaving
                        )
                    )
                }
            }
        }
    }

    fun onPlayAudioClicked(soundUrl: String) {
        // Set audio loading
        _state.update { screenState ->
            val contentState = screenState.contentState as? ContentState.Content
                ?: throw IllegalStateException("State is not Content, when clicking to listen audio button")

            screenState.copy(
                contentState = contentState.copy(
                    audioState = AudioState.Loading
                )
            )
        }

        Log.d("DictionaryVM", "audioState: ${_state.value}")
        startListenAudioUseCase(
            soundUrl = soundUrl,
            onStartPlaying = {
                setPlayingAudioState()
            },
            onComplete = {
                setNotPlayingAudioState()
            },
            onError = { errorMessage ->
                setNotPlayingAudioState()
                emitError(errorMessage)
            }
        )
    }

    fun onStopAudioClicked() {
        stopListenAudioUseCase(
            onComplete = {
                setNotPlayingAudioState()
            },
            onError = { errorMessage ->
                emitError(errorMessage = errorMessage)
            }
        )
    }

    fun onTextUpdate(newText: String) {
        _state.update { currentState ->
            currentState.copy(query = newText)
        }
    }

    private fun emitError(errorMessage: String) {
        viewModelScope.launch {
            _effects.emit(
                DictionaryScreenEffect.ShowSnackbar(
                    message = errorMessage,
                    isSuccess = false
                )
            )
        }
    }

    private fun setNotPlayingAudioState() {
        _state.update { screenState ->
            val contentState = screenState.contentState as? ContentState.Content
                ?: throw IllegalStateException("State is not Content, when clicking to listen audio button")

            screenState.copy(
                contentState = contentState.copy(
                    audioState = AudioState.NotPlaying
                )
            )
        }
    }

    private fun setPlayingAudioState() {
        _state.update { screenState ->
            val contentState = screenState.contentState as? ContentState.Content
                ?: throw IllegalStateException("State is not Content, when clicking to listen audio button")

            screenState.copy(
                contentState = contentState.copy(
                    audioState = AudioState.Playing
                )
            )
        }
    }
}