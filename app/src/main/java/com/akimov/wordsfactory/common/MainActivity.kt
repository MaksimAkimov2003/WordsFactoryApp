package com.akimov.wordsfactory.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.glance.appwidget.updateAll
import com.akimov.domain.dictionary.mediaPlayer.AppMediaPlayer
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.feature.widget.AppWidget
import com.akimov.wordsfactory.navigation.common.AppNavigation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val appMediaPlayer by inject<AppMediaPlayer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordsFactoryTheme {
                AppNavigation()
            }
        }
    }

    override fun onStop() {
        updateWidget()
        super.onStop()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateWidget() {
        GlobalScope.launch {
            AppWidget().updateAll(context = applicationContext)
        }
    }

    override fun onDestroy() {
        appMediaPlayer.releasePlayer()
        super.onDestroy()
    }
}