package ubb.victors3136.epigraphmobile.ui.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun DataButton(navController: NavHostController){
    EpigraphButton(
        Icons.Filled.Person,
        "Data"
    ) { navController.navigate("form") }
}