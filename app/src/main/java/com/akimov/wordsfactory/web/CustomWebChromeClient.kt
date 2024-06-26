package com.akimov.wordsfactory.web

import android.view.View
import android.webkit.WebChromeClient

class CustomWebChromeClient(
    private val showVideo: (view: View) -> Unit
) : WebChromeClient() {
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)

        view?.let {
            showVideo(it)
        }
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
    }
}