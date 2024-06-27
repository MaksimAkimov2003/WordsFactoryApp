package com.akimov.wordsfactory.feature.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.headingH3
import com.akimov.wordsfactory.common.theme.headingH5
import com.akimov.wordsfactory.common.theme.paragraphMedium
import com.akimov.wordsfactory.common.theme.primaryGradient

private const val SHAPE = 22

@Composable
fun WidgetUi(
    allWordsCount: Int,
    studiedWordsCount: Int
) {
    Column(
        modifier = Modifier
            .background(
                color = Color.White,
                shape =
                RoundedCornerShape(SHAPE.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryGradient
                        )
                    ),
                    shape = AbsoluteRoundedCornerShape(
                        topLeft = SHAPE.dp,
                        topRight = SHAPE.dp
                    )
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 5.dp, bottom = 4.dp),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headingH3,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.my_dictionary),
                style = MaterialTheme.typography.headingH5
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = buildString {
                    append("$allWordsCount")
                    append(" ")
                    append(stringResource(R.string.words))
                },
                style = MaterialTheme.typography.paragraphMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.remembered_words),
                style = MaterialTheme.typography.headingH5
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = buildString {
                    append("$studiedWordsCount")
                    append(" ")
                    append(stringResource(R.string.words))
                },
                style = MaterialTheme.typography.paragraphMedium,
                color = Color.Black.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Studied(studiedWordsCount: Int) {
    TextRaw(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        mainText = stringResource(R.string.remembered_words),
        helperText = buildString {
            append("$studiedWordsCount")
            append(" ")
            append(stringResource(R.string.words))
        }
    )
}

@Composable
private fun TextRaw(
    modifier: Modifier = Modifier,
    mainText: String,
    helperText: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mainText,
            style = MaterialTheme.typography.headingH5
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = helperText,
            style = MaterialTheme.typography.paragraphMedium,
            color = Color.Black.copy(alpha = 0.5f)
        )
    }
}

@Preview
@Composable
private fun WidgetUiPreview() {
    WordsFactoryTheme {
        WidgetUi(
            allWordsCount = 3125,
            studiedWordsCount = 41
        )
    }
}
