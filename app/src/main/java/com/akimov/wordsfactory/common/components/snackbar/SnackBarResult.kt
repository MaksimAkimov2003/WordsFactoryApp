package com.akimov.wordsfactory.common.components.snackbar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.success
import kotlinx.coroutines.delay

@Composable
fun SnackbarResult(
    data: SnackbarData,
    isSuccess: Boolean
) {
    Snackbar(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(
                    id = if (isSuccess) R.drawable.ic_success else R.drawable.ic_cancel
                ),
                contentDescription = null,
                tint = if (isSuccess) MaterialTheme.colorScheme.success else MaterialTheme.colorScheme.error,
            )
            Text(
                text = data.visuals.message,
                modifier = Modifier.padding(start = 12.dp),
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun SnackbarPreview() {
    WordsFactoryTheme {
        val hostState = remember {
            SnackbarHostState()
        }

        LaunchedEffect(key1 = Unit) {
            delay(3000L)
            hostState.showSnackbar("Result message")
        }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = hostState,
                    snackbar = {
                        SnackbarResult(data = it, isSuccess = false)
                    }
                )
            }
        ) {

        }
    }
}