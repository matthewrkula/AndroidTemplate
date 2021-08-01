package com.mattkula.template.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Yellow800 = Color(0xFFF29F05)
val Red300 = Color(0xFFEA6D7E)

val Color.Companion.MutedRed get() = Color(0xFFB1434E)
val Color.Companion.NeonGreen get() = Color(0xFFC4C92C)
val Color.Companion.NeonPink get() = Color(0xFFFF3C38)

val Color.Companion.CoolBlack get() = Color(0xFF252323)
val Color.Companion.CoolGold get() = Color(0xFFE0A81C)

val AppColors = lightColors(
    primary = Yellow800,
    onPrimary = Color.Black,
    primaryVariant = Yellow800,
    secondary = Yellow800,
    onSecondary = Color.Black,
    error = Red300,
    onError = Color.Black,
    surface = Color.CoolGold,
    onSurface = Color.White,
    background = Color.CoolBlack,
    onBackground = Color.White,
)

val BrightColors = lightColors(
    primary = Yellow800,
    onPrimary = Color.Black,
    primaryVariant = Yellow800,
    secondary = Yellow800,
    onSecondary = Color.Black,
    error = Red300,
    onError = Color.Black,
    surface = Color.NeonGreen,
    onSurface = Color.White,
    background = Color.NeonPink,
    onBackground = Color.White,
)
