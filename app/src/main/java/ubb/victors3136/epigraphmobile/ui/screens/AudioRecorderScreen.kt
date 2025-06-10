package ubb.victors3136.epigraphmobile.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ubb.victors3136.epigraphmobile.ui.buttons.EpigraphButton
import ubb.victors3136.epigraphmobile.ui.buttons.RecordButtonWithPermissions
import ubb.victors3136.epigraphmobile.ui.components.EpigraphFooter
import ubb.victors3136.epigraphmobile.ui.components.EpigraphHeader
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox

@Composable
fun AudioRecorderScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.Companion.fillMaxSize(),
        containerColor = Color.Companion.Transparent,
        topBar = { EpigraphHeader("Epigraph Mobile") },
        bottomBar = {
            EpigraphFooter(
                { RecordButtonWithPermissions() },
                {
                    EpigraphButton(
                        Icons.Filled.Person,
                        "Data"
                    ) { navController.navigate("form") }
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.Companion
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp, vertical = 36.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.Companion.height(16.dp))
                EpigraphTextBox(text = "Welcome to Epigraph Mobile")
                EpigraphTextBox(text = "Press the button below to transcribe :D")
                Spacer(modifier = Modifier.Companion.weight(1f))
            }
        }
    )
}