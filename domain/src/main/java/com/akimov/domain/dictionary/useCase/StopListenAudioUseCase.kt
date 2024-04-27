package com.akimov.domain.dictionary.useCase

import com.akimov.domain.dictionary.mediaPlayer.AppMediaPlayer

class StopListenAudioUseCase(
    private val appMediaPlayer: AppMediaPlayer
) {
    operator fun invoke(
        onComplete: () -> Unit,
        onError: (message: String) -> Unit
    ) {
        appMediaPlayer.stopListen(
            onComplete = {
                onComplete()
            },
            onError = {
                onError("Error during playing audio")
            }
        )
    }
}