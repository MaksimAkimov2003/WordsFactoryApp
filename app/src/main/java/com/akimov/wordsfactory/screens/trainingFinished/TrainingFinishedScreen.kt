package com.akimov.wordsfactory.screens.trainingFinished

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.domain.training.model.WordTrainingDto
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.components.button.AppFilledButton
import com.akimov.wordsfactory.common.components.button.AppFilledButtonWithProgressBar
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.heading1
import com.akimov.wordsfactory.common.theme.onBackgroundVariant
import com.akimov.wordsfactory.common.theme.paragraphMedium
import com.akimov.wordsfactory.screens.trainingFinished.presentation.TrainingFinishedEffect
import com.akimov.wordsfactory.screens.trainingFinished.presentation.TrainingFinishedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrainingFinishedScreen(
    correct: Int,
    incorrect: Int,
    navigateToQuestions: (List<WordTrainingDto>) -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<TrainingFinishedViewModel>()
    val isLoading = viewModel.isLoading.collectAsState()

    BackHandler {
        viewModel.onBackClicked()
    }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TrainingFinishedEffect.StartTrainingAgain -> {
                    navigateToQuestions(effect.words)
                }

                TrainingFinishedEffect.NavigateBack -> {
                    navigateBack()
                }
            }
        }
    }

    TrainingFinishedStateless(
        correct = correct,
        incorrect = incorrect,
        getIsLoading = { isLoading.value },
        onAgainClicked = remember(viewModel) {
            {
                viewModel.onAgainClicked()
            }
        },
        onBackClicked = remember(viewModel) {
            {
                viewModel.onBackClicked()
            }
        }
    )
}

@Composable
private fun TrainingFinishedStateless(
    correct: Int,
    incorrect: Int,
    getIsLoading: () -> Boolean,
    onAgainClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onBackClicked
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onBackgroundVariant,
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Logo()

            Spacer(modifier = Modifier.height(32.dp))

            Tittle()

            Spacer(modifier = Modifier.height(8.dp))

            Body(correct = correct, incorrect = incorrect)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                getIsLoading = getIsLoading,
                onAgainClicked = onAgainClicked
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.finished),
        contentDescription = null,
    )
}

@Composable
private fun Body(correct: Int, incorrect: Int) {
    Text(
        text = buildString {
            append(stringResource(R.string.correct))
            append(" ")
            append(correct)
        },
        style = MaterialTheme.typography.paragraphMedium
    )

    Text(
        text = buildString {
            append(stringResource(R.string.incorrect))
            append(" ")
            append(incorrect)
        },
        style = MaterialTheme.typography.paragraphMedium
    )
}

@Composable
private fun Tittle() {
    Text(
        text = stringResource(R.string.training_is_finished),
        style = MaterialTheme.typography.heading1,
    )
}

@Composable
private fun Button(
    getIsLoading: () -> Boolean,
    onAgainClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var buttonHeight: Int by remember {
        mutableIntStateOf(0)
    }
    if (getIsLoading()) {
        AppFilledButtonWithProgressBar(
            modifier = modifier,
            buttonHeight = buttonHeight
        ) {
            onAgainClicked()
        }
    } else {
        AppFilledButton(
            modifier = modifier.then(
                Modifier.onGloballyPositioned {
                    buttonHeight = it.size.height
                }
            ),
            getText = { stringResource(R.string.again) }
        ) {
            onAgainClicked()
        }
    }
}

@Preview
@Composable
private fun TrainingFinishedScreenPreview() {
    WordsFactoryTheme {
        TrainingFinishedStateless(
            correct = 5,
            incorrect = 3,
            getIsLoading = { false },
            onAgainClicked = { },
            onBackClicked = { }
        )
    }
}
