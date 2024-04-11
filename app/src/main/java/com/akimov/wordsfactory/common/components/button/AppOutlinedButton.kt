package com.akimov.wordsfactory.common.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.buttonMedium
import com.akimov.wordsfactory.common.theme.onBackgroundVariant

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    getText: @Composable () -> String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
        ),
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier.padding(),
            text = getText(),
            style = MaterialTheme.typography.buttonMedium,
            color = MaterialTheme.colorScheme.onBackgroundVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    WordsFactoryTheme {
        AppOutlinedButton(getText = { "Primary Button" }) {}
    }
}