package com.akimov.domain.dictionary.useCase

import com.akimov.domain.dictionary.mediaPlayer.AppMediaPlayer

class StartListenAudioUseCase(
    private val appMediaPlayer: AppMediaPlayer
) {
    operator fun invoke(
        soundUrl: String,
        onStartPlaying: () -> Unit,
        onComplete: () -> Unit,
        onError: (message: String) -> Unit
    ) {
        appMediaPlayer.startListen(
            soundUrl = soundUrl,
            onStartPlaying = {
                onStartPlaying()
            },
            onComplete = {
                onComplete()
            },
            onError = {
                onError("Error during playing audio")
            }
        )
    }
}