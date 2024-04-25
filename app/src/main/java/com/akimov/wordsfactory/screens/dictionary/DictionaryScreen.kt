package com.akimov.wordsfactory.screens.dictionary

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.akimov.domain.dictionary.model.MeaningDto
import com.akimov.domain.dictionary.model.WordInfoDto
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.components.button.AppFilledButton
import com.akimov.wordsfactory.common.components.textField.AppTextField
import com.akimov.wordsfactory.common.extensions.replaceSlashesWithBrackets
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.heading1
import com.akimov.wordsfactory.common.theme.paragraphMedium
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun DictionaryScreen() {
    Text("Dictionary")
}

@Composable
private fun DictionaryScreenStateless(
    state: DictionaryScreenState,
    onAddToDictionaryClicked: (word: String) -> Unit,
    onSearchClick: (word: String) -> Unit,
) {
    val contentState by rememberUpdatedState(newValue = state.contentState)

    var buttonScrollVisibility by remember {
        mutableStateOf(true)
    }

    val buttonVisibility by remember {
        derivedStateOf {
            (contentState is ContentState.Content) && buttonScrollVisibility
        }
    }
    Scaffold(
        topBar = {
            DictionaryTopBar(
                onSearchClick = onSearchClick
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = buttonVisibility,
                enter = slideInVertically(
                    spring(
                        stiffness = Spring.StiffnessVeryLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    ),
                ) { it * 2 },
                exit = slideOutVertically(
                    spring(
                        stiffness = Spring.StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    )
                ) { it * 2 },
            ) {
                AppFilledButton(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                    getText = { stringResource(R.string.add_to_dictionary) },
                    onClick = {
                        onAddToDictionaryClicked(
                            (contentState as ContentState.Content).wordInfo.word
                        )
                    })
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        when (contentState) {
            ContentState.NotInput -> DictionaryNoInputState { paddingValues }
            ContentState.Loading -> DictionaryLoadingState { paddingValues }
            ContentState.NotResults -> Unit
            is ContentState.Content -> DictionaryContentState(
                getPaddingValues = { paddingValues },
                getWordInfo = { (contentState as ContentState.Content).wordInfo },
                getAudioState = { (contentState as ContentState.Content).audioState },
                changeButtonVisibility = { isVisible ->
                    buttonScrollVisibility = isVisible
                }
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6_pro")
@Composable
private fun DictionaryScreenStatelessPreview() {
    WordsFactoryTheme {
        val meanings = buildList {
            repeat(10) {
                this.add(
                    MeaningDto(
                        definition = "The practice or skill of preparing"
                                + " food by combining, mixing, and heating ingredients.",
                        example = "he developed an interest in cooking."
                    )
                )
            }
        }
        DictionaryScreenStateless(state = DictionaryScreenState(
            query = "Cooking",
            contentState = ContentState.Content(
                wordInfo = WordInfoDto(
                    word = "Cooking",
                    meanings = meanings.toImmutableList(),
                    soundUrl = "https://www.sound.com",
                    partOfSpeech = "Noun",
                    transcription = "/ˈkʊkɪŋ/"
                ), audioState = AudioState.Loading
            ),
        ), onAddToDictionaryClicked = {}) {

        }
    }
}

@Composable
private fun DictionaryLoadingState(
    paddingValues: () -> PaddingValues = { PaddingValues(0.dp) }
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(top = paddingValues().calculateTopPadding())
                .align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DictionaryLoadingStatePreview() {
    WordsFactoryTheme {
        DictionaryLoadingState()
    }
}

@Composable
private fun DictionaryNoInputState(
    paddingValues: () -> PaddingValues = { PaddingValues() }
) {
    Box(
        modifier = Modifier
            .padding(top = paddingValues().calculateTopPadding())
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(
                painter = painterResource(id = R.drawable.cool_kids_empty),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.no_word),
                style = MaterialTheme.typography.heading1
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.enter_word),
                style = MaterialTheme.typography.paragraphMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DictionaryNoInputStatePreview() {
    WordsFactoryTheme {
        DictionaryNoInputState()
    }
}

@Composable
private fun DictionaryContentState(
    getWordInfo: () -> WordInfoDto,
    getPaddingValues: () -> PaddingValues,
    getAudioState: () -> AudioState,
    changeButtonVisibility: (visible: Boolean) -> Unit,
) {
    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            Log.d(
                "DictionaryContentState",
                "onPreScroll: available = ${available.y}, source = $source"
            )
            if (available.y > 0) {
                changeButtonVisibility(true)
            } else if (available.y < 0) {
                changeButtonVisibility(false)
            }
            return super.onPreScroll(available, source)
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            Log.d(
                "DictionaryContentState",
                "onPostScroll: consumed = ${consumed.y}, available = ${available.y}, source = $source"
            )
            return super.onPostScroll(consumed, available, source)
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            Log.d("DictionaryContentState", "onPreFling: available = ${available.y}")
            return super.onPreFling(available)
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            Log.d(
                "DictionaryContentState",
                "onPostFling: consumed = ${consumed.y}, available = ${available.y}"
            )
            return super.onPostFling(consumed, available)
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = getPaddingValues().calculateTopPadding() + 16.dp)
            .nestedScroll(nestedScrollConnection),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        item {
            TittleRow(
                getWord = { getWordInfo().word },
                getTranscription = { getWordInfo().transcription },
                getSoundUrl = { getWordInfo().soundUrl },
                onSoundIconClicked = {},
                getAudioState = getAudioState
            )

            Spacer(modifier = Modifier.height(16.dp))

            PartOfSpeechRaw(getPartOfSpeech = { getWordInfo().partOfSpeech })

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.meanings),
                style = MaterialTheme.typography.heading1,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        items(items = getWordInfo().meanings) { meaning ->
            DefinitionListItem(meaning = meaning)
        }
    }
}

@Composable
private fun DefinitionListItem(meaning: MeaningDto) {
    meaning.definition?.let { definition ->
        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = definition,
                style = MaterialTheme.typography.paragraphMedium,
                color = Color.Black
            )

            Row(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.example),
                    style = MaterialTheme.typography.paragraphMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.width(4.dp))

                meaning.example?.let { example ->
                    Text(
                        text = example,
                        style = MaterialTheme.typography.paragraphMedium,
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun PartOfSpeechRaw(
    getPartOfSpeech: () -> String?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.part_of_speech),
            style = MaterialTheme.typography.heading1,
            color = Color.Black
        )

        getPartOfSpeech()?.let {
            Text(
                text = it, style = MaterialTheme.typography.paragraphMedium, color = Color.Black
            )
        }
    }
}

@Composable
private fun TittleRow(
    getWord: () -> String,
    getTranscription: () -> String?,
    getSoundUrl: () -> String?,
    getAudioState: () -> AudioState,
    onSoundIconClicked: (soundUrl: String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = getWord(), style = MaterialTheme.typography.heading1, color = Color.Black
        )

        getTranscription()?.let { transcription ->
            Text(
                text = transcription.replaceSlashesWithBrackets(),
                style = MaterialTheme.typography.paragraphMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        getSoundUrl()?.let { soundUrl ->
            if (getAudioState() == AudioState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Icon(
                    modifier = Modifier.clickable {
                        onSoundIconClicked(soundUrl)
                    },
                    imageVector = if (getAudioState() == AudioState.NotPlaying) ImageVector.vectorResource(
                        id = R.drawable.ic_volume
                    )
                    else ImageVector.vectorResource(id = R.drawable.ic_stop),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
private fun DictionaryContentStatePreview() {
    WordsFactoryTheme {
        DictionaryContentState(
            getWordInfo = {
                WordInfoDto(
                    word = "Cooking",
                    meanings = persistentListOf(
                        MeaningDto(
                            definition = "The practice or skill of preparing" +
                                    " food by combining, mixing, and heating ingredients.",
                            example = "he developed an interest in cooking."
                        ), MeaningDto(
                            definition = "The practice or skill of preparing "
                                    + "food by combining, mixing, and heating ingredients.",
                            example = "he developed an interest in cooking."
                        )
                    ),
                    soundUrl = "https://www.sound.com",
                    partOfSpeech = "Noun",
                    transcription = "/ˈkʊkɪŋ/"
                )
            },
            getPaddingValues = { PaddingValues(0.dp) },
            getAudioState = { AudioState.NotPlaying },
            changeButtonVisibility = {}
        )
    }
}

@Composable
private fun DictionaryTopBar(
    onSearchClick: (word: String) -> Unit
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    AppTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp
            ),
        getText = { text },
        updateText = { newValue ->
            text = newValue
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    indication = null, interactionSource = MutableInteractionSource()
                ) {
                    onSearchClick(text)
                },
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            onSearchClick(text)
        })
    )
}

@Preview(showBackground = true)
@Composable
private fun DictionaryTopBarPreview() {
    WordsFactoryTheme {
        DictionaryTopBar(onSearchClick = {})
    }
}
