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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun EpigraphButton(text: String, onClick: () -> Unit = {}) = Button(
    colors = ButtonDefaults.buttonColors(
        containerColor = ThemeProvider.get().accentColor(),
        contentColor = Color.White
    ),
    content = { Text(text) },
    onClick = { onClick() }
)
@Composable
fun EpigraphTextBox(text: String, modifier: Modifier = Modifier, primary: Boolean = true){
    Text(text, modifier,
        color = if(primary)ThemeProvider.get().primaryText() else ThemeProvider.get().secondaryText(),
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
            EpigraphButton("Cancel"){
                recorder.reset()
                isRecording = false
                showSaveCancel = false
            }

            EpigraphButton("Save"){
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
        EpigraphButton("Record"){
            try {
                recorder.apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                    setOutputFile(filePath)
                    prepare()
                    start()
                }
                isRecording = true
                showSaveCancel = true
            } catch (e: Exception) {
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
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun Main() {
    val backgroundColor = ThemeProvider.get().primaryBg()
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
                Header("Epigraph")
                Column(modifier = Modifier.padding(32.dp)) {
                    EpigraphTextBox(
                        text = "Welcome to Epigraph Mobile",
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(top = 16.dp)
                    )
                    EpigraphTextBox(
                        text = "Press the button below to transcribe :D",
                    )
                    RecordButtonWithPermissions()
                    EpigraphButton("Toggle theme"){ ThemeProvider.toggle() }
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