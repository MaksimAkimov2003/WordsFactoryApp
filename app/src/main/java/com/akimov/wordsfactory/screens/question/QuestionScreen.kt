package com.akimov.wordsfactory.screens.question

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.common.components.progress.AppLinearProgressIndicator
import com.akimov.wordsfactory.common.extensions.checkCondition
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.heading1
import com.akimov.wordsfactory.common.theme.onBackgroundVariant
import com.akimov.wordsfactory.common.theme.paragraphLarge
import com.akimov.wordsfactory.common.theme.success
import com.akimov.wordsfactory.common.uiModels.HighlightType
import com.akimov.wordsfactory.common.uiModels.VariantUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionScreen(
    navigateNext: () -> Unit
) {
    val viewModel = koinViewModel<QuestionViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is QuestionScreenEffect.NavigateNext -> navigateNext()
            }
        }
    }
    QuestionScreenStateless(
        state = state,
        onVariantClick = remember(viewModel) {
            { index ->
                viewModel.onVariantClick(index)
            }
        }
    )
}

@Composable
private fun QuestionScreenStateless(
    state: QuestionScreenState,
    onVariantClick: (index: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(
            text = state.question,
            style = MaterialTheme.typography.heading1
        )

        VariantsList(
            getIsClickEnabled = { state.isSelectVariantEnabled },
            getVariants = { state.variants },
            onVariantClick = onVariantClick
        )

        ProgressIndicator(
            getProgress = { state.progress }
        )
    }
}

@Composable
private fun ProgressIndicator(
    getProgress: () -> Float
) {
    Box(
        modifier = Modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AppLinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .clip(RoundedCornerShape(100.dp)),
            progress = getProgress(),
            color = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFEF4949),
                    Color(0xFFE3562A),
                    Color(0xFFF29F3F),
                )
            )
        )
    }
}

@Composable
private fun VariantsList(
    getVariants: () -> ImmutableList<VariantUI>,
    getIsClickEnabled: () -> Boolean,
    onVariantClick: (index: Int) -> Unit
) {
    getVariants().forEachIndexed { index, variant ->
        Spacer(modifier = Modifier.height(16.dp))
        VariantItem(
            getVariant = { variant },
            getIndex = { index },
            getIsClickEnabled = getIsClickEnabled,
            onVariantClick = onVariantClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun VariantItem(
    getVariant: () -> VariantUI,
    modifier: Modifier = Modifier,
    getIndex: () -> Int,
    getIsClickEnabled: () -> Boolean,
    onVariantClick: (index: Int) -> Unit
) {
    val (borderColor, borderWidth) =
        when (getVariant().highlightType) {
            HighlightType.CORRECT -> MaterialTheme.colorScheme.success to 2.dp
            HighlightType.WRONG -> MaterialTheme.colorScheme.error to 2.dp
            HighlightType.DEFAULT -> MaterialTheme.colorScheme.tertiary to 1.dp
        }

    val backgroundColor = when (getVariant().highlightType) {
        HighlightType.CORRECT -> MaterialTheme.colorScheme.success.copy(alpha = 0.1f)
        HighlightType.WRONG -> MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
        HighlightType.DEFAULT -> MaterialTheme.colorScheme.background
    }

    Row(
        modifier = modifier.then(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .checkCondition(
                    condition = {
                        getIsClickEnabled()
                    },
                    ifTrue = {
                        clickable {
                            onVariantClick(getIndex())
                        }
                    },
                )
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Variant letter
        Text(
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            text = getVariant().letter,
            style = MaterialTheme.typography.paragraphLarge,
            color = MaterialTheme.colorScheme.onBackgroundVariant,
        )

        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
            text = getVariant().text,
            style = MaterialTheme.typography.paragraphLarge,
            color = MaterialTheme.colorScheme.onBackgroundVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuestionScreenStatelessPreview() {
    WordsFactoryTheme {
        QuestionScreenStateless(
            state = QuestionScreenState(
                question = "The practice or skill of preparing food" +
                        " by combining, mixing, and heating ingredients.",
                variants = persistentListOf(
                    VariantUI(
                        letter = "A.",
                        text = "Cooking",
                        highlightType = HighlightType.CORRECT
                    ),
                    VariantUI(
                        letter = "B.",
                        text = "Baking",
                        highlightType = HighlightType.WRONG
                    ),
                    VariantUI(
                        letter = "C.",
                        text = "Frying",
                        highlightType = HighlightType.DEFAULT
                    ),
                ),
                progress = 0.9f,
                isSelectVariantEnabled = true,
            ),
            onVariantClick = { }
        )
    }
}