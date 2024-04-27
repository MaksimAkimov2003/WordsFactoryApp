package com.akimov.wordsfactory.common.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.common.extensions.checkCondition
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.buttonMedium

@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    getText: @Composable () -> String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
        enabled = enabled,
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
fun AppFilledButtonWithProgressBar(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonHeight: Int,
    onClick: () -> Unit,
) {
    val localDensity = LocalDensity.current
    Button(
        modifier = modifier.then(Modifier
            .checkCondition(
                condition = { buttonHeight == 0 },
                ifTrue = { this.wrapContentHeight() },
                ifFalse = {
                    val height = with(localDensity) {
                        buttonHeight.toDp()
                    }
                    this.height(height)
                }
            )),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary,
        ),
        onClick = onClick
    ) {
        val height = with(localDensity) {
            buttonHeight.toDp()
        }
        Box(modifier = Modifier.size(height)) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview
@Composable
private fun ButtonWithProgressBarPreview() {
    WordsFactoryTheme {
        AppFilledButtonWithProgressBar(
            buttonHeight = 56,
            onClick = {},
            enabled = false
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    WordsFactoryTheme {
        AppFilledButton(getText = { "Primary Button" }) {}
    }
}