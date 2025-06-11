package ubb.victors3136.epigraphmobile.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import ubb.victors3136.epigraphmobile.ui.buttons.DataButton
import ubb.victors3136.epigraphmobile.ui.buttons.RecordAudioButton
import ubb.victors3136.epigraphmobile.ui.buttons.SaveRecordingButton
import ubb.victors3136.epigraphmobile.ui.components.EpigraphErrorBox
import ubb.victors3136.epigraphmobile.ui.components.EpigraphFooter
import ubb.victors3136.epigraphmobile.ui.components.EpigraphHeader
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox

@Composable
fun AudioRecorderScreen(navController: NavHostController) {
    val (isRecording, setIsRecording) = remember { mutableStateOf(false) }
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
                EpigraphTextBox(text = "Welcome to Epigraph Mobile")

                if (permissionGranted) {
                    EpigraphTextBox(text = "Press the button below to transcribe :D")
                } else {
                    EpigraphErrorBox("You need to enable permissions to use this app")
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
                            setSavedFilePath =  {newPath-> { savedFilePath = newPath }},
                            setRecordingState = setIsRecording,

                        )
                    }
                } else {
                    null
                },
                if (isRecording) {
                    {
                        SaveRecordingButton {
                            recorder.stop()
                            recorder.reset()
                            setIsRecording(false)
                            showSaveCancel = false
                            savedFilePath = filePath
                            filePath =
                                "${context.cacheDir.absolutePath}/recording_${System.currentTimeMillis()}.m4a"
                        }
                    }
                } else {
                    { DataButton(navController) }
                },
            )
        },
    )
}