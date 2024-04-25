package com.akimov.wordsfactory.common.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.buttonMedium

@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    getText: @Composable () -> String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(),
            text = getText(),
            style = MaterialTheme.typography.buttonMedium
        )
    }
}

@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        onClick = onClick
    ) {
        content()
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    WordsFactoryTheme {
        AppFilledButton(getText = { "Primary Button" }) {}
    }
}