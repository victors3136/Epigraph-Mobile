package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ubb.victors3136.epigraphmobile.ui.ThemeProvider
import ubb.victors3136.epigraphmobile.ui.getComplementaryIcon

@Composable
fun ThemeButton() {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Companion.Transparent,
            contentColor = ThemeProvider.get().primaryText()
        ),
        onClick = { ThemeProvider.toggle() }) {
        Icon(
            imageVector = getComplementaryIcon(ThemeProvider.mode()),
            tint = ThemeProvider.get().primaryText(),
            contentDescription = "Change theme"
        )
    }
}