package ubb.victors3136.epigraphmobile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ubb.victors3136.epigraphmobile.theme.ThemeProvider

@Composable
fun EpigraphButton(text: String, onClick: () -> Unit = {}) = Button(
    colors = ButtonDefaults.buttonColors(
        containerColor = ThemeProvider.get().primaryAccent(),
        contentColor = Color.White
    ),
    content = { Text(text) },
    onClick = { onClick() }
)

@Composable
fun EpigraphTextBox(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    primary: Boolean = true,
) {
    Text(
        text = text,
        modifier = modifier,
        color = if (primary) ThemeProvider.get().primaryText()
        else ThemeProvider.get().secondaryText(),
        style = style,
    )
}


@Composable
fun RecordAudioButton(context: Context) {
    val recorder = remember { MediaRecorder(context) }
    var isRecording by remember { mutableStateOf(false) }
    var showSaveCancel by remember { mutableStateOf(false) }
    var filePath by remember {
        mutableStateOf(
            "${context.cacheDir.absolutePath}/recording_${System.currentTimeMillis()}.m4a"
        )
    }
    var savedFilePath by remember { mutableStateOf<String?>(null) }

    val playAudio: (String) -> Unit = { path ->
        Toast.makeText(context, "Recording done!", Toast.LENGTH_SHORT).show()
        MediaPlayer().apply {
            setDataSource(path)
            prepare()
            start()
        }
    }

    if (!isRecording && savedFilePath != null) {
        LaunchedEffect(savedFilePath) {
            savedFilePath?.let { playAudio(it) }
            savedFilePath = null
        }
    }

    if (isRecording && showSaveCancel) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            EpigraphButton("Cancel") {
                recorder.reset()
                isRecording = false
                showSaveCancel = false
            }

            EpigraphButton("Save") {
                recorder.stop()
                recorder.reset()
                isRecording = false
                showSaveCancel = false
                savedFilePath = filePath
                filePath =
                    "${context.cacheDir.absolutePath}/recording_${System.currentTimeMillis()}.m4a"
            }
        }
    } else {
        EpigraphButton("Record") {
            runCatching {
                recorder.apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                    setOutputFile(filePath)
                    prepare()
                    start()
                }
            }.onSuccess {
                isRecording = true
                showSaveCancel = true
            }.onFailure { e ->
                e.printStackTrace()
                Toast.makeText(context, "Recording failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun RecordButtonWithPermissions() {
    val context = LocalContext.current
    val activity = context as Activity
    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    if (!permissionGranted) {
        LaunchedEffect(Unit) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                123
            )
        }
    }

    if (permissionGranted) {
        RecordAudioButton(context)
    } else {
        EpigraphTextBox("Microphone permission required.")
    }
}

@Composable
fun EpigraphHeader(text: String) {
    val backgroundColor = ThemeProvider.get().secondaryBg()
    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_96),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 12.dp)
            )
            EpigraphTextBox(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun EpigraphFooter(
    vararg buttons: @Composable () -> Unit,
) {
    Surface(
        color = ThemeProvider.get().primaryBg(),
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            buttons.forEach { it() }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = { EpigraphHeader("Epigraph Mobile") },
        bottomBar = {
            EpigraphFooter(
                { RecordButtonWithPermissions() },
                { EpigraphButton("Data") { navController.navigate("userForm") } },
                { EpigraphButton("Theme") { ThemeProvider.toggle() } },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp, vertical = 36.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                EpigraphTextBox(text = "Welcome to Epigraph Mobile")
                EpigraphTextBox(text = "Press the button below to transcribe :D")
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}

@Composable
fun BackButton(navController: NavHostController) {
    EpigraphButton("Back") { navController.popBackStack() }
}


@Composable
fun EpigraphTextField(value: String, label: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it) },
        label = { EpigraphTextBox(label) },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = ThemeProvider.get().primaryText()
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = ThemeProvider.get().primaryText(),
            focusedBorderColor = ThemeProvider.get().primaryAccent(),
            focusedLabelColor =  ThemeProvider.get().primaryAccent(),
            focusedPlaceholderColor =  ThemeProvider.get().primaryText(),
            unfocusedTextColor = ThemeProvider.get().secondaryText(),
            unfocusedBorderColor = ThemeProvider.get().secondaryAccent(),
            unfocusedLabelColor =  ThemeProvider.get().secondaryAccent(),
            unfocusedPlaceholderColor = ThemeProvider.get().secondaryText(),
            cursorColor = ThemeProvider.get().primaryAccent(),

            )
    )
}

@Composable
fun EpigraphCheckbox(
    checked: Boolean,
    label: String,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = androidx.compose.material3.CheckboxDefaults.colors(
                checkedColor = ThemeProvider.get().primaryAccent(),
                uncheckedColor = ThemeProvider.get().secondaryAccent(),
                checkmarkColor = ThemeProvider.get().primaryText()
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        EpigraphTextBox(text = label, primary = true)
    }
}

@Composable
fun UserInfoFormScreen(navController: NavHostController) {
    val context = LocalContext.current
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var consent by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { EpigraphHeader("User Info") },
        bottomBar = {
            EpigraphFooter({
                EpigraphButton("Submit") {
                    if (age.isNotEmpty() && gender.isNotEmpty()) {
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
            }, { BackButton(navController) })
        },
        containerColor = Color.Transparent,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp, vertical = 36.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EpigraphTextBox("Please fill in your information:")
                EpigraphTextField(age, "Age") {
                    if (it.all { character -> character.isDigit() }) age = it
                }
                EpigraphTextField(gender, "Gender") { gender = it }
                EpigraphCheckbox(consent, "I consent to my data being stored") { consent = it }
            }
        }
    )
}

@Composable
fun Main() {
    val navController = rememberNavController()
    val backgroundColor = ThemeProvider.get().primaryBg()

    Surface(
        modifier = Modifier
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}