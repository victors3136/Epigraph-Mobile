package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable

@Composable
fun SaveRecordingButton(onClick: () -> Unit) {
    EpigraphButton(Icons.Filled.Save, "Save") { onClick() }
}