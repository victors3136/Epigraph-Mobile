package ubb.victors3136.epigraphmobile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import ubb.victors3136.epigraphmobile.theme.ThemeProvider

@Composable
fun RecordAudioButton(context: Context) {
    val recorder = remember { MediaRecorder(context) }
    var isRecording by remember { mutableStateOf(false) }
    val filePath = remember {
        "${context.cacheDir.absolutePath}/recording_${System.currentTimeMillis()}.m4a"
    }

    Button(onClick = {
        if (isRecording) {
            recorder.stop()
            recorder.reset()
            isRecording = false
        } else {
            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(filePath)
                prepare()
                start()
            }
            isRecording = true
        }
    }) {
        Text(if (isRecording) "Stop Recording" else "Start Recording")
    }
}
@Composable
fun RecordButtonWithPermissions() {
    val context = LocalContext.current
    val activity = context as Activity

    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
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
        Text("Microphone permission required.")
    }
}

@Composable
fun Header(text: String) {
    val backgroundColor = ThemeProvider.get().secondaryBg()
    val textColor = ThemeProvider.get().primaryText()

    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun Main() {
    val textColor = ThemeProvider.get().primaryText()
    val backgroundColor = ThemeProvider.get().primaryBg()
    val buttonColor = ThemeProvider.get().accentColor()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight(),
            containerColor = Color.Transparent,
            content = { innerPadding ->
                Header("Main")
                Column(modifier = Modifier.padding(32.dp)) {
                    Text(
                        text = "Hello World!",
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp),
                        color = textColor,
                    )
                    RecordButtonWithPermissions()
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = Color.White
                        ),
                        content = { Text("Toggle theme") },
                        onClick = { ThemeProvider.toggle() }
                    )
                }
            }
        )
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