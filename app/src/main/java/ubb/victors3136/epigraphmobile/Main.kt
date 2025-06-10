package ubb.victors3136.epigraphmobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ubb.victors3136.epigraphmobile.theme.ThemeProvider

@Composable
fun Main() {
    val navController = rememberNavController()
    val backgroundColor = ThemeProvider.get().primaryBg()

    Surface(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController)
            }
            composable("userForm") {
                UserInfoFormScreen(navController)
            }
        }
    }
}