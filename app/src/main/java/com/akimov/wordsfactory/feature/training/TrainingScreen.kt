package com.akimov.wordsfactory.feature.training

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.components.button.AppFilledButton
import com.akimov.wordsfactory.common.extensions.mapToCompose
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.extraLarge
import com.akimov.wordsfactory.common.theme.heading1
import com.akimov.wordsfactory.feature.training.presentation.TrainingScreenEffect
import com.akimov.wordsfactory.feature.training.presentation.TrainingScreenState
import com.akimov.wordsfactory.feature.training.presentation.TrainingState
import com.akimov.wordsfactory.feature.training.presentation.TrainingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrainingScreen(navigateNext: (List<WordTrainingDto>) -> Unit) {
    val viewModel = koinViewModel<TrainingViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TrainingScreenEffect.NavigateNext -> navigateNext(
                    effect.wordsForTraining
                )
            }
        }
    }

    TrainingScreenStateless(
        getState = { state.value },
        onStartTrainingClicked = remember(viewModel) {
            {
                viewModel.onStartTraining()
            }
        }
    )
}

@Composable
fun TrainingScreenStateless(
    getState: () -> TrainingScreenState,
    onStartTrainingClicked: () -> Unit
) {
    when (val state = getState()) {
        TrainingScreenState.NoWords -> NoWordsState()
        is TrainingScreenState.EnoughWords ->
            EnoughWordsState(
                wordsCount = state.wordsCount,
                getTrainingState = { state.trainingState },
                onStartTrainingClicked = onStartTrainingClicked,
            )
    }
}

@Composable
private fun EnoughWordsState(
    wordsCount: Int,
    getTrainingState: () -> TrainingState,
    onStartTrainingClicked: () -> Unit,
) {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            AnnotatedText(wordsCount = wordsCount)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.start_the_training),
                style = MaterialTheme.typography.heading1,
                textAlign = TextAlign.Center,
            )
            when (val state = getTrainingState()) {
                TrainingState.NotStarted ->
                    NotStartedState(onStartTrainingClicked)

                is TrainingState.Started ->
                    StartedState(
                        getNumber = { state.convertSecondsToText() },
                        getColor = { state.color.mapToCompose() },
                        getPercentage = { state.percentage },
                    )
            }
        }
    }
}

private const val INNER_PADDING_DP = 32
private const val STROKE_WIDTH_DP = 8

@Composable
private fun ColumnScope.StartedState(
    getNumber: () -> String,
    getColor: @Composable () -> Color,
    getPercentage: () -> Float,
) {
    Spacer(modifier = Modifier.weight(0.5f))

    val animatedPercentage = animateFloatAsState(targetValue = getPercentage())
    val animatedColor = animateColorAsState(targetValue = getColor())
    Text(
        modifier = Modifier.drawBehind {
            val diameter = maxOf(size.width, size.height) + 2 * INNER_PADDING_DP.dp.toPx()
            drawArc(
                color = animatedColor.value,
                startAngle = 270f,
                sweepAngle = 360f * animatedPercentage.value,
                useCenter = false,
                style = Stroke(width = STROKE_WIDTH_DP.dp.toPx()),
                topLeft = Offset(
                    x = (size.width - diameter) / 2,
                    y = (size.height - diameter) / 2
                ),
                size = Size(diameter, diameter)
            )
        },
        text = getNumber(),
        color = getColor(),
        style = MaterialTheme.typography.extraLarge.copy(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        ),
    )
    Spacer(
        modifier = Modifier.weight(0.5f),
    )
}

@Composable
private fun ColumnScope.NotStartedState(onStartTrainingClicked: () -> Unit) {
    Spacer(modifier = Modifier.weight(1f))
    AppFilledButton(
        modifier =
        Modifier.Companion
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        getText = { stringResource(id = R.string.start) },
    ) {
        onStartTrainingClicked()
    }
}

@Composable
private fun AnnotatedText(wordsCount: Int) {
    val annotatedText =
        buildAnnotatedString {
            append(stringResource(R.string.there_are))
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("$wordsCount")
            }
            append(stringResource(R.string.words_in_your_dictionary))
        }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.heading1,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun NoWordsState() {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.no_words_training),
            style = MaterialTheme.typography.heading1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoWordsPreview() {
    WordsFactoryTheme {
        TrainingScreenStateless(
            getState = { TrainingScreenState.NoWords },
            onStartTrainingClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrainingAvailablePreview() {
    WordsFactoryTheme {
        TrainingScreenStateless(
            getState =
            {
                TrainingScreenState.EnoughWords(
                    wordsCount = 10,
                    trainingState = TrainingState.NotStarted,
                )
            },
            onStartTrainingClicked = {},
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro")
@Composable
private fun StartedTrainingPreview() {
    WordsFactoryTheme {
        TrainingScreenStateless(
            getState =
            {
                TrainingScreenState.EnoughWords(
                    wordsCount = 10,
                    trainingState =
                    TrainingState.Started(
                        secondsToStart = 3,
                    ),
                )
            },
            onStartTrainingClicked = {},
        )
    }
}
