package com.akimov.wordsfactory.web

import android.webkit.WebView
import android.webkit.WebViewClient
import com.akimov.wordsfactory.common.UiConstants

class CustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return if (url != null && url.startsWith(UiConstants.HOST)) {
            view?.loadUrl(url)
            false
        } else {
            true
        }
    }
}