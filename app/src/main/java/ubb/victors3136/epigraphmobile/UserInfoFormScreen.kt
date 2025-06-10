package ubb.victors3136.epigraphmobile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserInfoFormScreen(navController: NavHostController) {
    val context = LocalContext.current
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var consent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val (cachedAge, cachedGender, cachedConsent) = loadUserInfo(context)
        age = cachedAge.toString()
        gender = cachedGender
        consent = cachedConsent
    }

    Scaffold(
        topBar = { EpigraphHeader("User Info") },
        bottomBar = {
            EpigraphFooter(
                {
                    BackButton(navController)
                },
                {
                    EpigraphButton(Icons.Filled.Done, "Submit") {
                        val ageInt = age.toIntOrNull()
                        if (ageInt != null && ageInt >= 0 && gender.isNotEmpty()) {
                            CoroutineScope(Dispatchers.IO).launch {
                                saveUserInfo(context, ageInt, gender, consent)
                            }
                            Toast.makeText(context, "Data submitted!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(
                                context,
                                "Please complete all fields.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
            )
        },
        containerColor = Color.Companion.Transparent,
        content = { innerPadding ->
            Column(
                modifier = Modifier.Companion
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp, vertical = 36.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EpigraphTextBox("Please fill in your information:")
                EpigraphIntField(age, "Age") { age = it }
                EpigraphTextField(gender, "Gender") { gender = it }
                EpigraphCheckbox(consent, "I consent to my data being stored") { consent = it }
            }
        }
    )
}