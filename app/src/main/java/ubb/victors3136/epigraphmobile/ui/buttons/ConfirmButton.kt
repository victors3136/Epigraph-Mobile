package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable

@Composable
fun ConfirmButton(onClick: () -> Unit) {
    EpigraphButton(Icons.Filled.Done, "Confirm") {
        onClick()
    }
}