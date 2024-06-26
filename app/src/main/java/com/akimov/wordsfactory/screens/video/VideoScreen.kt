package com.akimov.wordsfactory.screens.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebView.VisualStateCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.akimov.wordsfactory.common.UiConstants
import com.akimov.wordsfactory.screens.video.presentation.VideoViewModel
import com.akimov.wordsfactory.web.CustomWebChromeClient
import com.akimov.wordsfactory.web.CustomWebViewClient
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoScreen(
    navigateBack: () -> Unit,
    navigateToPlayer: () -> Unit
) {
    val viewModel: VideoViewModel = koinViewModel()
    val isLoading = viewModel.isLoading.collectAsState()

    DisposableEffect(key1 = true) {
        onDispose {
            Log.d("VideoScreen", "onDispose")
        }
    }

    VideoScreenStateless(
        getIsLoading = { isLoading.value },
        onComplete = remember(viewModel) { { viewModel.onLoadComplete() } },
        navigateBack = navigateBack,
        navigateToPlayer = navigateToPlayer
    )
}

@Composable
private fun VideoScreenStateless(
    getIsLoading: () -> Boolean,
    onComplete: () -> Unit,
    navigateBack: () -> Unit,
    navigateToPlayer: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        WebWrapper(
            onComplete = onComplete,
            navigateBack = navigateBack,
            navigateToPlayer = navigateToPlayer
        )

        if (getIsLoading()) {
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun WebWrapper(
    navigateBack: () -> Unit,
    onComplete: () -> Unit,
    navigateToPlayer: () -> Unit
) {
    val webView = rememberWebView(
        showVideo = { view ->
            VideoViewHolder.setView(view)
            navigateToPlayer()
        },
        onComplete = onComplete
    )

    BackHandler {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            navigateBack()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { webView }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun rememberWebView(
    showVideo: (videoView: View) -> Unit,
    onComplete: () -> Unit
): WebView {
    val context = LocalContext.current

    val webViewState = rememberSaveable { Bundle() }

    val webView = remember {
        WebView(context).apply {
            this.loadUrl(UiConstants.URL)
            this.settings.javaScriptEnabled = true
            this.webViewClient = CustomWebViewClient()
            this.webChromeClient = CustomWebChromeClient { videoView ->
                showVideo(videoView)
            }
            this.restoreState(webViewState)
            this.postVisualStateCallback(
                1,
                object : VisualStateCallback() {
                    override fun onComplete(requestId: Long) =
                        onComplete()
                }
            )
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            webView.saveState(webViewState)
        }
    }

    return webView
}