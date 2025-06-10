package ubb.victors3136.epigraphmobile.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import ubb.victors3136.epigraphmobile.ui.ThemeProvider

@Composable
fun EpigraphTextBox(
    text: String,
    modifier: Modifier = Modifier.Companion,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    primary: Boolean = true,
) {
    Text(
        text = text,
        modifier = modifier,
        color = if (primary) ThemeProvider.get().primaryText()
        else ThemeProvider.get().secondaryText(),
        style = style,
    )
}