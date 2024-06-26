package com.akimov.wordsfactory.screens.video

import android.view.View
import androidx.compose.runtime.Composable
import java.lang.ref.WeakReference

object VideoViewHolder {
    private var currentView: WeakReference<View?> = WeakReference(null)

    fun setView(view: View) {
        currentView = WeakReference(view)
    }

    fun removeView() {
        currentView.clear()
    }

    @Throws(IllegalStateException::class)
    @Composable
    fun getVideoView(): View {
        return currentView.get() ?: throw IllegalStateException("Video view is null")
    }
}