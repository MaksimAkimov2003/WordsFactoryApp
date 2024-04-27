package com.akimov.wordsfactory.common.uiModels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable

@Stable
data class SnackbarStateWrapper(
    val snackbarHostState: SnackbarHostState,
    var isSuccess: Boolean
)
