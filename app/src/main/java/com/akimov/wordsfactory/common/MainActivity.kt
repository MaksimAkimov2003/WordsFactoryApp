package com.akimov.wordsfactory.common

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        requestPermission()

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!isPermissionGranted) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
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