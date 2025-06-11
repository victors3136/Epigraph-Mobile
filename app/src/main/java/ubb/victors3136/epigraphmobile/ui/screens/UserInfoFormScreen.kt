package ubb.victors3136.epigraphmobile.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
        containerColor = Color.Transparent,
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 400.dp)
                            .padding(horizontal = 32.dp, vertical = 36.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EpigraphIntField(
                            value = age,
                            label = "Age",
                            onChange = { age = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        EpigraphTextField(
                            value = gender,
                            label = "Gender (woman/man/other)",
                            onChange = { gender = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        EpigraphCheckbox(
                            checked = consent,
                            label = "I consent to my data being stored",
                            onChange = { consent = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
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