package com.akimov.wordsfactory.common.theme

import android.app.Activity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme =
    lightColorScheme(
        primary = Color(0XFFE3562A),
        secondary = Color(0XFF65AAEA),
        background = Color.White,
        onPrimary = Color.White,
        onBackground = Color(0xFF78746D),
        tertiary = ink,
        error = Color(0xFFbacEF4949),
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
     */
    )

val ColorScheme.onBackgroundVariant: Color
    get() = inkDark

val ColorScheme.secondaryVariant: Color
    get() = lightGrey

val ColorScheme.success: Color
    get() = Color(0xFF5BA092)

val ColorScheme.primaryGradient: Color
    get() = Color(0xFFF29F3F)

@Composable
fun WordsFactoryTheme(content: @Composable () -> Unit) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
