package ubb.victors3136.epigraphmobile.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ubb.victors3136.epigraphmobile.network.uploadRecording
import ubb.victors3136.epigraphmobile.persistance.userDataStore
import ubb.victors3136.epigraphmobile.ui.animations.LoadingAnimation
import ubb.victors3136.epigraphmobile.ui.animations.RecordingAnimation
import ubb.victors3136.epigraphmobile.ui.animations.rememberRecordingTimer
import ubb.victors3136.epigraphmobile.ui.buttons.DataButton
import ubb.victors3136.epigraphmobile.ui.buttons.RecordAudioButton
import ubb.victors3136.epigraphmobile.ui.buttons.SubmitRecordingButton
import ubb.victors3136.epigraphmobile.ui.components.EpigraphFooter
import ubb.victors3136.epigraphmobile.ui.components.EpigraphHeader
import ubb.victors3136.epigraphmobile.ui.components.EpigraphLargeTextBox
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox


@Composable
fun AudioRecorderScreen(navController: NavHostController) {
    val (isRecording, setIsRecording) = remember { mutableStateOf(false) }
    val (isUploading, setIsUploading) = remember { mutableStateOf(false) }
    var uploadResponse by remember { mutableStateOf<String?>(null) }
    var uploadError by remember { mutableStateOf<Boolean>(false) }

    val context = LocalContext.current
    var recordingPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var internetPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        recordingPermission = permissions[Manifest.permission.RECORD_AUDIO] == true
        internetPermission = permissions[Manifest.permission.INTERNET] == true
    }

    fun hasAllRequiredPermissions() = recordingPermission && internetPermission

    LaunchedEffect(Unit) {
        if (!hasAllRequiredPermissions()) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.INTERNET
                )
            )
        }
    }

    val recorder = remember { MediaRecorder(context) }
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
        uploadError = false
        uploadResponse = null
        setIsRecording(false)
        savedFilePath = filePath
        val relativePath = "recording_${System.currentTimeMillis()}.m4a"
        filePath = "${context.cacheDir.absolutePath}/$relativePath"
        coroutineScope.launch {
            setIsUploading(true)
            val result = uploadRecording(savedFilePath!!, context.userDataStore)
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
                when {
                    !hasAllRequiredPermissions() -> EpigraphTextBox(
                        "You need to enable recording and internet permissions to use this app",
                        isError = true
                    )

                    isUploading -> LoadingAnimation()
                    uploadResponse != null -> EpigraphLargeTextBox(
                        uploadResponse!!,
                        isError = uploadError
                    )
                    isRecording -> RecordingAnimation(duration)
                    else -> EpigraphTextBox("Press the button below to transcribe :D")
                }
                Spacer(modifier = Modifier.Companion.weight(1f))
            }

        },
        bottomBar = {
            EpigraphFooter(
                if (recordingPermission) {
                    {
                        RecordAudioButton(
                            context = context,
                            isRecording = isRecording,
                            recorder = recorder,
                            savedFilePath = filePath,
                            setSavedFilePath = { newPath -> { savedFilePath = newPath } },
                            setRecordingState = setIsRecording,
                            prepare = {
                                uploadError = false
                                uploadResponse = null
                            }
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