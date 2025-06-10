package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import ubb.victors3136.epigraphmobile.ui.ThemeProvider

@Composable
fun EpigraphButton(
    icon: ImageVector,
    description: String? = null,
    color: Color = ThemeProvider.get().primaryText(),
    onClick: () -> Unit = {},
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Companion.Transparent,
            contentColor = ThemeProvider.get().primaryText()
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description ?: "",
            tint = color
        )
    }
}