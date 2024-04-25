package com.akimov.wordsfactory.screens.dictionary

import com.akimov.domain.dictionary.model.WordInfoDto

data class DictionaryScreenState(
    val query: String,
    val contentState: ContentState
)

sealed class ContentState {
    data object NotInput : ContentState()
    data object Loading : ContentState()
    data class Content(
        val wordInfo: WordInfoDto,
        val audioState: AudioState
    ) : ContentState()

    data object NotResults : ContentState()
}

enum class AudioState {
    NotPlaying,
    Playing,
    Loading
}
