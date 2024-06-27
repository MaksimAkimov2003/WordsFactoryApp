package com.akimov.wordsfactory.feature.video.player

import android.app.Activity
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.akimov.wordsfactory.feature.video.VideoViewHolder

@Composable
fun VideoPlayerScreen(
    videoView: View
) {
    val localContext = LocalContext.current
    val localView = LocalView.current

    DisposableEffect(key1 = true) {
        val controller = WindowInsetsControllerCompat((localContext as Activity).window, localView)
        controller.hide(WindowInsetsCompat.Type.statusBars())
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        onDispose {
            controller.show(WindowInsetsCompat.Type.statusBars())
            controller.show(WindowInsetsCompat.Type.navigationBars())
            VideoViewHolder.removeView()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { videoView }
    )
}