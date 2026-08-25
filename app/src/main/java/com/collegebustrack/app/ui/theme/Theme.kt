package com.collegebustrack.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BusBlue = Color(0xFF1565C0)
val LightBlue = Color(0xFFEAF3FF)
val Ink = Color(0xFF172033)
val Muted = Color(0xFF62708A)
val Border = Color(0xFFD8E1EE)
val Success = Color(0xFF16835B)

private val AppColors = lightColorScheme(
    primary = BusBlue, onPrimary = Color.White,
    primaryContainer = LightBlue, onPrimaryContainer = Ink,
    background = Color(0xFFF7FAFE), onBackground = Ink,
    surface = Color.White, onSurface = Ink,
    outline = Border
)

@Composable fun CollegeBusTheme(content: @Composable () -> Unit) = MaterialTheme(colorScheme = AppColors, content = content)
