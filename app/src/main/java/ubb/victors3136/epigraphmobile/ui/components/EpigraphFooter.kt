package ubb.victors3136.epigraphmobile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ubb.victors3136.epigraphmobile.ui.ThemeProvider

@Composable
fun EpigraphFooter(
    vararg buttons: @Composable () -> Unit,
) {
    Surface(
        color = ThemeProvider.get().primaryBg(),
        tonalElevation = 4.dp,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(16.dp)
            .padding(WindowInsets.Companion.navigationBars.asPaddingValues())
    ) {
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            buttons.forEach { it() }
        }
    }
}