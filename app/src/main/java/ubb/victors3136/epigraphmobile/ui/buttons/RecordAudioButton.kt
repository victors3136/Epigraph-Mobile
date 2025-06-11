package ubb.victors3136.epigraphmobile.ui.buttons

import android.content.Context
import android.media.MediaRecorder
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

private fun initRecording(recorder: MediaRecorder, filePath: String) {
    recorder.apply {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        setOutputFile(filePath)
        prepare()
        start()
    }
}

@Composable
fun RecordAudioButton(
    context: Context,
    isRecording: Boolean,
    recorder: MediaRecorder,
    savedFilePath: String?,
    setSavedFilePath: (String?) -> Unit,
    setRecordingState: (Boolean) -> Unit,
) {

    if (!isRecording && savedFilePath != null) {
        LaunchedEffect(savedFilePath) {
            setSavedFilePath(null)
        }
    }

    if (isRecording) {
        CancelRecordingButton {
            recorder.reset()
            setRecordingState(false)
        }
    } else {
        BeginRecordingButton {
            runCatching {
                initRecording(recorder, savedFilePath!!)
            }.onSuccess {
                setRecordingState(true)
            }.onFailure { e ->
                Toast.makeText(context, "Recording failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}