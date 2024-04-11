package com.akimov.wordsfactory.screens.onBoarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.components.button.AppTextButton
import com.akimov.wordsfactory.common.components.button.FilledButton
import com.akimov.wordsfactory.common.extensions.checkCondition
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.heading1
import com.akimov.wordsfactory.common.theme.paragraphMedium
import com.akimov.wordsfactory.common.theme.secondaryVariant
import kotlinx.coroutines.launch

private const val PAGE_COUNT = 3

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navigateForward: () -> Unit
) {
    val state = rememberPagerState { PAGE_COUNT }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val text = stringResource(id = R.string.skip)
        AppTextButton(modifier = Modifier
            .padding(top = 24.dp, end = 4.dp)
            .align(Alignment.End),
            getText = {
                text
            }) {
            navigateForward()
        }

        var maxElementHeight: Int by remember {
            mutableIntStateOf(0)
        }
        val localDensity = LocalDensity.current
        HorizontalPager(
            modifier = Modifier,
            state = state,
        ) { page ->
            val modifier = Modifier
                .padding(
                    top = 4.dp, start = 16.dp, end = 16.dp
                )
                .checkCondition(
                    condition = { maxElementHeight != 0 },
                    ifTrue = {
                        val heightDp = with(localDensity) {
                            maxElementHeight.toDp()
                        }
                        this.height(heightDp)
                    },
                    ifFalse = {
                        this.wrapContentHeight()
                    }
                )
                .onGloballyPositioned { layoutCoordinates ->
                    if (layoutCoordinates.size.height > maxElementHeight) {
                        maxElementHeight = layoutCoordinates.size.height
                    }
                }
            when (page) {
                0 -> {
                    val header = stringResource(R.string.learn_anytime_and_anywhere)
                    val text =
                        stringResource(R.string.quarantine_is_the_perfect_time_to_spend_your_day_learning_something_new_from_anywhere)
                    Content(
                        modifier = modifier,
                        getResourceId = { R.drawable.on_boarding_1 },
                        getHeader = { header },
                        getText = { text })
                }

                1 -> {
                    val header = stringResource(R.string.find_a_course_for_you)
                    val text =
                        stringResource(R.string.quarantine_is_the_perfect_time_to_spend_your_day_learning_something_new_from_anywhere_2)
                    Content(
                        modifier = modifier,
                        getResourceId = { R.drawable.on_boarding_2 },
                        getHeader = { header },
                        getText = { text })
                }

                2 -> {
                    val header = stringResource(R.string.improve_your_skills)
                    val text =
                        stringResource(id = R.string.quarantine_is_the_perfect_time_to_spend_your_day_learning_something_new_from_anywhere_2)
                    Content(modifier = modifier,
                        getResourceId = { R.drawable.on_boarding_3 },
                        getHeader = { header },
                        getText = { text })
                }
            }
        }
        Indicators(
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.CenterHorizontally),
            currentPage = state.currentPage
        )

        Spacer(modifier = Modifier.weight(1f))

        val stringResource = if (state.currentPage == PAGE_COUNT - 1)
            stringResource(R.string.lets_start) else stringResource(R.string.next)
        val coroutineScope = rememberCoroutineScope()

        FilledButton(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            getText = {
                stringResource
            }) {
            if (state.currentPage < PAGE_COUNT - 1) {
                coroutineScope.launch {
                    state.animateScrollToPage(state.currentPage + 1)
                }
            } else {
                navigateForward()
            }

        }
    }

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    getResourceId: () -> Int,
    getText: () -> String,
    getHeader: () -> String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = getResourceId()),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = getHeader(),
            style = MaterialTheme.typography.heading1,
        )

        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = getText(),
            style = MaterialTheme.typography.paragraphMedium
        )
    }
}

@Composable
private fun Indicators(modifier: Modifier, currentPage: Int) {
    Row(modifier = modifier) {
        for (pageNumber in 0..<PAGE_COUNT) {
            if (pageNumber == currentPage) {
                SelectedIndicator(
                    modifier = Modifier
                        .width(16.dp)
                        .height(8.dp)
                )
            } else {
                UnselectedIndicator(
                    modifier = Modifier.size(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
private fun UnselectedIndicator(
    modifier: Modifier = Modifier
) {
    val color = MaterialTheme.colorScheme.secondaryVariant
    Canvas(modifier = modifier) {
        drawCircle(
            color = color
        )
    }
}

@Composable
private fun SelectedIndicator(
    modifier: Modifier = Modifier
) {
    val color = MaterialTheme.colorScheme.secondary
    val radius = with(LocalDensity.current) {
        8.dp.toPx()
    }
    Canvas(
        modifier = modifier,
    ) {
        drawRoundRect(
            color = color, cornerRadius = CornerRadius(radius)
        )
    }
}

@Preview
@Composable
private fun OnBoardingScreenPreview() {
    WordsFactoryTheme {
        OnBoardingScreen {}
    }
}