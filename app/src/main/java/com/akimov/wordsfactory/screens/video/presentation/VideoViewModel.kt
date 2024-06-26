package com.akimov.wordsfactory.screens.video.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        Log.d("VideoViewModel", "init")
    }

    fun onLoadComplete() {
        _isLoading.value = false
    }
}