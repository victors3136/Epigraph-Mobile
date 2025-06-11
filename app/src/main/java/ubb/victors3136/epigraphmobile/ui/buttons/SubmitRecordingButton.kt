package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable

@Composable
fun SubmitRecordingButton(onClick: () -> Unit) {
    EpigraphButton(Icons.AutoMirrored.Filled.Send, "Submit") { onClick() }
}