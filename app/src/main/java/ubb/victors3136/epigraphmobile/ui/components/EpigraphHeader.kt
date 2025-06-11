package ubb.victors3136.epigraphmobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ubb.victors3136.epigraphmobile.R
import ubb.victors3136.epigraphmobile.R.drawable
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider
import ubb.victors3136.epigraphmobile.ui.buttons.ThemeButton

@Composable
fun EpigraphHeader(text: String) {
    val backgroundColor = ThemeProvider.get().secondaryBg()
    Surface(
        color = backgroundColor,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            modifier = Modifier.Companion.padding(
                start = 16.dp,
                top = 32.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            Image(
                painter = painterResource(id = drawable.logo_96),
                contentDescription = "Logo",
                modifier = Modifier.Companion
                    .size(40.dp)
                    .padding(end = 12.dp)
            )
            EpigraphTextBox(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.Companion.weight(1f))
            ThemeButton()
        }
    }
}