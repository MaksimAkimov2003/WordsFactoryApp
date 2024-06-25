package com.akimov.wordsfactory.screens.dictionary.presentation

import com.akimov.domain.dictionary.model.WordWithMeaningsDto

data class DictionaryScreenState(
    val query: String,
    val contentState: ContentState
)

sealed class ContentState {
    data object NotInput : ContentState()
    data object Loading : ContentState()
    data class Content(
        val wordInfo: WordWithMeaningsDto,
        val audioState: AudioState,
        val wordSavingState: WordSavingState
    ) : ContentState()

    data object NotResults : ContentState()
}

enum class AudioState {
    NotPlaying,
    Playing,
    Loading
}

enum class WordSavingState {
    NotSaving,
    Saving
}
