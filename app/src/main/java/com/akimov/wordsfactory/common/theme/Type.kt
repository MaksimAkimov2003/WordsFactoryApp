package com.akimov.wordsfactory.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.glance.unit.ColorProvider

val Typography.extraLarge: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp,
    )

val Typography.paragraphLarge: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = inkDarkGrey
    )

val Typography.paragraphMedium: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = inkDarkGrey
    )

val Typography.heading1: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = inkDark
    )

val Typography.headingH5: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        color = Color.Black
    )

val Typography.headingH3: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Color.White
    )

val Typography.buttonMedium: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = Color.White
    )

val Typography.buttonSmall: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = inkDarkGrey
    )

val GlanceH5 = androidx.glance.text.TextStyle(
    fontWeight = androidx.glance.text.FontWeight.Bold,
    fontSize = 32.sp,
    color = ColorProvider(Color.White)
)
val GlanceH3 = androidx.glance.text.TextStyle(
    fontWeight = androidx.glance.text.FontWeight.Medium,
    fontSize = 20.sp,
    color = ColorProvider(Color.Black)
)
val GlanceParagraphMedium = androidx.glance.text.TextStyle(
    fontWeight = androidx.glance.text.FontWeight.Normal,
    fontSize = 14.sp,
    color = ColorProvider(inkDarkGrey)
)

