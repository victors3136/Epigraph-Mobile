package ubb.victors3136.epigraphmobile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BackButton(navController: NavHostController) {
    EpigraphButton(
        Icons.AutoMirrored.Filled.ArrowLeft,
        "Back"
    ) { navController.popBackStack(route = "home", inclusive = false) }
}