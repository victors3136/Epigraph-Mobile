package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun BackButton(navController: NavHostController) {
    EpigraphButton(
        Icons.AutoMirrored.Filled.ArrowLeft,
        "Back", ThemeProvider.get().primaryText()
    ) { navController.popBackStack(route = "recorder", inclusive = false) }
}