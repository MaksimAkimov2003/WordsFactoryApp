package com.akimov.wordsfactory.common.components.textField

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.paragraphMedium

@Composable
fun AppTextField(
    getText: () -> String,
    updateText: (newValue: String) -> Unit,
    modifier: Modifier = Modifier,
    getLabelText: @Composable (() -> String)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        modifier = modifier,
        value = getText(),
        onValueChange = updateText,
        label = if (getLabelText != null) {
            {
                Text(
                    text = getLabelText(),
                    style = MaterialTheme.typography.paragraphMedium
                )
            }
        } else {
            null
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
        ),
        trailingIcon = trailingIcon,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}

@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    WordsFactoryTheme {
        AppTextField(
            getText = { "" },
            updateText = {},
            getLabelText = { "Name" },
        )
    }
}