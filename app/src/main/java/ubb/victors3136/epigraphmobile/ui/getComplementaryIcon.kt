package ubb.victors3136.epigraphmobile.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny

fun getComplementaryIcon(mode: ThemeMode) = when (mode) {
    ThemeMode.DARK -> Icons.Filled.WbSunny
    ThemeMode.LIGHT -> Icons.Filled.Nightlight
}