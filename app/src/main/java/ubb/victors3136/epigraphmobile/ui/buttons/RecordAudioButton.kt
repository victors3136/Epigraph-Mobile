package ubb.victors3136.epigraphmobile.ui.buttons

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Square
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import ubb.victors3136.epigraphmobile.ui.ThemeProvider

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
            EpigraphButton(Icons.Filled.Square, "Stop", ThemeProvider.get().recordButtonColor()) {
                recorder.reset()
                isRecording = false
                showSaveCancel = false
            }

            EpigraphButton(Icons.Filled.Save, "Save") {
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
        EpigraphButton(Icons.Filled.Circle, "Record", ThemeProvider.get().recordButtonColor()) {
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