package com.akimov.data.dictionary.mediaPlayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import com.akimov.domain.dictionary.mediaPlayer.AppMediaPlayer

class AppMediaPlayerImpl(
    private val mediaPlayer: MediaPlayer
) : AppMediaPlayer {
    override fun startListen(
        soundUrl: String,
        onStartPlaying: () -> Unit,
        onComplete: () -> Unit,
        onError: (exception: Throwable) -> Unit
    ) {
        try {
            mediaPlayer.run {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(soundUrl)
                setOnPreparedListener {
                    onStartPlaying()
                }
                setOnCompletionListener {
                    reset()
                    onComplete()
                }
                prepare()
                start()
            }

        } catch (e: Throwable) {
            Log.d("Media player", e.message.toString())
            onError(e)
        }
    }

    override fun stopListen(
        onComplete: () -> Unit,
        onError: (exception: Throwable) -> Unit
    ) {
        try {
            mediaPlayer.stop()
            mediaPlayer.reset()
            onComplete()
        } catch (e: Throwable) {
            onError(e)
        }
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }
}