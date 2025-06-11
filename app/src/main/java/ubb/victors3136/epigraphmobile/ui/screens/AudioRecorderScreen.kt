package ubb.victors3136.epigraphmobile.ui.screens

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ubb.victors3136.epigraphmobile.R.*
import ubb.victors3136.epigraphmobile.network.uploadRecording
import ubb.victors3136.epigraphmobile.ui.buttons.DataButton
import ubb.victors3136.epigraphmobile.ui.buttons.RecordAudioButton
import ubb.victors3136.epigraphmobile.ui.buttons.SubmitRecordingButton
import ubb.victors3136.epigraphmobile.ui.components.EpigraphErrorBox
import ubb.victors3136.epigraphmobile.ui.components.EpigraphFooter
import ubb.victors3136.epigraphmobile.ui.components.EpigraphHeader
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun LoadingAnimation() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = ThemeProvider.get().primaryAccent(),
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Uploading...",
            style = MaterialTheme.typography.bodyMedium,
            color = ThemeProvider.get().primaryText()
        )
    }
}

@Composable
fun UploadResultBox(text: String, isError: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(Color.Black)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = drawable.logo_96),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .alpha(0.15f),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (isError) {
                EpigraphErrorBox(text)
            } else {
                EpigraphTextBox(text)
            }
        }
    }
}


@Composable
fun AudioRecorderScreen(navController: NavHostController) {
    val (isRecording, setIsRecording) = remember { mutableStateOf(false) }
    val (isUploading, setIsUploading) = remember { mutableStateOf(false) }
    var uploadResponse by remember { mutableStateOf<String?>(null) }
    var uploadError by remember { mutableStateOf<Boolean>(false) }

    val context = LocalContext.current
    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val recorder = remember { MediaRecorder(context) }
    var showSaveCancel by remember { mutableStateOf(false) }
    var filePath by remember {
        mutableStateOf(
            "${context.cacheDir.absolutePath}/recording_${System.currentTimeMillis()}.m4a"
        )
    }
    var savedFilePath by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    fun saveRecording() {
        recorder.stop()
        recorder.reset()
        setIsRecording(false)
        showSaveCancel = false
        savedFilePath = filePath
        val relativePath = "recording_${System.currentTimeMillis()}.m4a"
        filePath = "${context.cacheDir.absolutePath}/$relativePath"
        coroutineScope.launch {
            setIsUploading(true)
            val result = uploadRecording(savedFilePath!!, context)
            uploadResponse = result
            uploadError = result.startsWith("Error", ignoreCase = true)
            setIsUploading(false)
        }
    }

    val duration by if (isRecording) {
        rememberRecordingTimer(
            onMaxReached = {
                if (isRecording) saveRecording()
            }
        )
    } else {
        remember { mutableLongStateOf(0L) }
    }

    Scaffold(
        modifier = Modifier.Companion.fillMaxSize(),
        containerColor = Color.Companion.Transparent,
        topBar = { EpigraphHeader("Epigraph Mobile") },
        content = { innerPadding ->
            Column(
                modifier = Modifier.Companion
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp, vertical = 36.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.Companion.height(16.dp))
                when {
                    !permissionGranted -> EpigraphErrorBox("You need to enable permissions to use this app")
                    isUploading -> LoadingAnimation()
                    uploadResponse != null -> UploadResultBox(uploadResponse!!, uploadError)
                    isRecording -> RecordingAnimation(duration)
                    else -> EpigraphTextBox("Press the button below to transcribe :D")
                }
                Spacer(modifier = Modifier.Companion.weight(1f))
            }

        },
        bottomBar = {
            EpigraphFooter(
                if (permissionGranted) {
                    {
                        RecordAudioButton(
                            context = context,
                            isRecording = isRecording,
                            recorder = recorder,
                            savedFilePath = filePath,
                            setSavedFilePath = { newPath -> { savedFilePath = newPath } },
                            setRecordingState = setIsRecording,
                        )
                    }
                } else {
                    null
                },
                if (isRecording) {
                    { SubmitRecordingButton { saveRecording() } }
                } else {
                    { DataButton(navController) }
                },
            )
        },
    )
}