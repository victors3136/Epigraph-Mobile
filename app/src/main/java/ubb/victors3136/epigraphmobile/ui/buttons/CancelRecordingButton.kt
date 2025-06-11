package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.runtime.Composable
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun CancelRecordingButton(onClick: () -> Unit) {
    EpigraphButton(Icons.Filled.Square, "Stop", ThemeProvider.get().redLike()) { onClick() }
}