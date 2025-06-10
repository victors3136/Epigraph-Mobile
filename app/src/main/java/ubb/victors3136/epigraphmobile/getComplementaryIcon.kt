package ubb.victors3136.epigraphmobile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import ubb.victors3136.epigraphmobile.theme.ThemeMode

fun getComplementaryIcon(mode: ThemeMode) = when (mode) {
    ThemeMode.DARK -> Icons.Filled.WbSunny
    ThemeMode.LIGHT -> Icons.Filled.Nightlight
}