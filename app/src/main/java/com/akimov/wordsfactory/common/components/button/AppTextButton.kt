package com.akimov.wordsfactory.common.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.theme.buttonSmall

@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    getText: () -> String,
    onClick: () -> Unit
) {
    androidx.compose.material3.TextButton(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        onClick = onClick,
    ) {
        Text(
            text = getText(),
            style = MaterialTheme.typography.buttonSmall
        )
    }
}