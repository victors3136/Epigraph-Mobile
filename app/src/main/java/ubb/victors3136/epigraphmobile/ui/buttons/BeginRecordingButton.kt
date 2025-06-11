package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun BeginRecordingButton(onClick: () -> Unit) {
    EpigraphButton(Icons.Filled.Circle, "Record", ThemeProvider.get().redLike()) { onClick() }
}