package ubb.victors3136.epigraphmobile.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun EpigraphTextBox(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    isError: Boolean = false,
) {
    Text(
        text = text,
        modifier = modifier,
        color = if (isError) ThemeProvider.get().redLike() else ThemeProvider.get().primaryText(),
        style = style.copy(fontSize = 22.sp),
    )
}

