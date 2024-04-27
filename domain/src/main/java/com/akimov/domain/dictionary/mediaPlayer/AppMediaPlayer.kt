package com.akimov.domain.dictionary.mediaPlayer

interface AppMediaPlayer {
    fun startListen(
        soundUrl: String,
        onStartPlaying: () -> Unit,
        onComplete: () -> Unit,
        onError: (exception: Throwable) -> Unit
    )

    fun stopListen(
        onComplete: () -> Unit,
        onError: (exception: Throwable) -> Unit
    )

    fun releasePlayer()
}
