package ubb.victors3136.epigraphmobile.ui.animations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun LoadingAnimation() {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = ThemeProvider.get().primaryAccent(),
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.Companion.height(12.dp))
        EpigraphTextBox(
            "Uploading...",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}