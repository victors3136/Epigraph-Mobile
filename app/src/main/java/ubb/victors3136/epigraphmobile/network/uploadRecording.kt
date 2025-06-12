package ubb.victors3136.epigraphmobile.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import ubb.victors3136.epigraphmobile.logging.LoggingService
import ubb.victors3136.epigraphmobile.persistance.loadUserInfo
import java.io.File

suspend fun uploadRecording(filePath: String, dataStore: DataStore<Preferences>): String {
    val (age, gender, consent) = loadUserInfo(dataStore)

    val client = OkHttpClient()
    val file = File(filePath)
    if (!file.exists()) {
        LoggingService.error("Recording file does not exist")
        return "Recording file does not exist"
    }
    val requestBody = MultipartBody.Builder().setType(MultipartBody.Companion.FORM)
        .addFormDataPart("file", file.name, file.asRequestBody("audio/m4a".toMediaTypeOrNull()))
        .addFormDataPart("age", age?.toString() ?: "null")
        .addFormDataPart("gender", gender)
        .addFormDataPart("consent", consent.toString())
        .build()

    LoggingService.info(requestBody.toString())

    val request = Request.Builder()
        .url("https://postman-echo.com/post")
        .post(requestBody)
        .build()

    return withContext(Dispatchers.IO) {
        try {
            client.newCall(request).execute().use { response ->
                response.body?.string() ?: "No response body provided"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}