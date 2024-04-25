package com.akimov.wordsfactory.common.components.bottomNavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.paragraphMedium
import com.akimov.wordsfactory.navigation.bottomNav.NavBarItem

private const val BOTTOM_BAR_HEIGHT_DP = 64
private const val PADDING_TOP_DP = 12

@Composable
fun AppBottomBar(
    selectedItem: NavBarItem,
    changeSelectedItem: (new: NavBarItem) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(BOTTOM_BAR_HEIGHT_DP.dp)
            .drawBehind {
                val path = Path().apply {
                    moveTo(0f, BOTTOM_BAR_HEIGHT_DP.dp.toPx())
                    arcTo(
                        rect = Rect(
                            left = 0f,
                            top = 0f,
                            right = BOTTOM_BAR_HEIGHT_DP.dp.toPx(),
                            bottom = BOTTOM_BAR_HEIGHT_DP.dp.toPx()
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    lineTo(size.width - (BOTTOM_BAR_HEIGHT_DP / 2).dp.toPx(), 0f)
                    arcTo(
                        rect = Rect(
                            left = size.width - BOTTOM_BAR_HEIGHT_DP.dp.toPx(),
                            top = 0f,
                            right = size.width,
                            bottom = BOTTOM_BAR_HEIGHT_DP.dp.toPx()
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                    lineTo(size.width, size.height)
                }

                drawPath(
                    path = path,
                    color = colorScheme.tertiary,
                    style = Stroke(width = 1.dp.toPx())
                )
            },
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AppNavItem(
            isSelected = selectedItem == NavBarItem.DICTIONARY,
            vectorResource = R.drawable.nav_dictionary,
            stringResource = R.string.dictionary,
            onClick = { changeSelectedItem(NavBarItem.DICTIONARY) },
            modifier = Modifier.padding(top = PADDING_TOP_DP.dp)
        )

        AppNavItem(
            isSelected = selectedItem == NavBarItem.TRAINING,
            vectorResource = R.drawable.nav_training,
            stringResource = R.string.training,
            onClick = { changeSelectedItem(NavBarItem.TRAINING) },
            modifier = Modifier.padding(top = PADDING_TOP_DP.dp)
        )

        AppNavItem(
            isSelected = selectedItem == NavBarItem.VIDEO,
            vectorResource = R.drawable.nav_video,
            stringResource = R.string.video,
            onClick = { changeSelectedItem(NavBarItem.VIDEO) },
            modifier = Modifier.padding(top = PADDING_TOP_DP.dp)
        )

    }
}

@Composable
private fun AppNavItem(
    isSelected: Boolean,
    vectorResource: Int,
    stringResource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color =
        if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.tertiary

    Column(
        modifier = modifier.then(
            Modifier
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) {
                    onClick()
                }
        )
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            imageVector = ImageVector.vectorResource(id = vectorResource),
            contentDescription = null,
            tint = color,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(stringResource),
            style = MaterialTheme.typography.paragraphMedium,
            color = color
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun AppBottomNavigationPreview() {
    WordsFactoryTheme {
        Scaffold(
            bottomBar = {
                AppBottomBar(
                    selectedItem = NavBarItem.TRAINING,
                    changeSelectedItem = { }
                )
            }
        ) {
        }
    }
}