package ubb.victors3136.epigraphmobile.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import ubb.victors3136.epigraphmobile.persistance.loadUserInfo
import ubb.victors3136.epigraphmobile.persistance.saveUserInfo
import ubb.victors3136.epigraphmobile.ui.buttons.BackButton
import ubb.victors3136.epigraphmobile.ui.buttons.ConfirmButton
import ubb.victors3136.epigraphmobile.ui.components.EpigraphFooter
import ubb.victors3136.epigraphmobile.ui.components.EpigraphHeader
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox
import ubb.victors3136.epigraphmobile.ui.form_fields.EpigraphCheckbox
import ubb.victors3136.epigraphmobile.ui.form_fields.EpigraphIntField
import ubb.victors3136.epigraphmobile.ui.form_fields.EpigraphTextField

fun validate(age: Int?, gender: String): Boolean =
    age != null && age >= 0 && (gender in listOf("woman", "man", "other"))

@Composable
fun UserInfoFormScreen(navController: NavHostController) {
    val context = LocalContext.current
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var consent by remember { mutableStateOf(false) }

    fun onSuccess(age: Int, gender: String, consent: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            saveUserInfo(context, age, gender, consent)
        }
        Toast.makeText(context, "Data submitted!", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }

    fun onFail() = Toast.makeText(
        context,
        "Make sure all data is valid before saving.",
        Toast.LENGTH_SHORT
    ).show()


    suspend fun onInit() {
        val (cachedAge, cachedGender, cachedConsent) = loadUserInfo(context)
        age = cachedAge?.toString() ?: ""
        gender = cachedGender
        consent = cachedConsent
    }

    LaunchedEffect(Unit) { onInit() }

    Scaffold(
        topBar = { EpigraphHeader("User Info") },
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
                EpigraphTextField(gender, "Gender (woman/man/other)") { gender = it }
                EpigraphCheckbox(consent, "I consent to my data being stored") { consent = it }
            }
        },
        bottomBar = {
            EpigraphFooter(
                { BackButton(navController) },
                {
                    ConfirmButton {
                        age.toIntOrNull()
                            ?.takeIf { validate(it, gender) }
                            ?.let { onSuccess(it, gender, consent) }
                            ?: onFail()
                    }
                },
            )
        }
    )
}