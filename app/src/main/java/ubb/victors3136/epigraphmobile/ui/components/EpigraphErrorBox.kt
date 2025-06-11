package ubb.victors3136.epigraphmobile.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun EpigraphErrorBox(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium){
    Text(
        text = text,
        modifier = modifier,
        color = ThemeProvider.get().redLike(),
        style = style,
    )
}