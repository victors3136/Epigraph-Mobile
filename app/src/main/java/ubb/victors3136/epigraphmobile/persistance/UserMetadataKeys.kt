package ubb.victors3136.epigraphmobile.persistance

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object UserMetadataKeys {
    val AGE = intPreferencesKey("user_age")
    val GENDER = stringPreferencesKey("user_gender")
    val CONSENT = booleanPreferencesKey("user_consent")
}

val Context.userDataStore by preferencesDataStore(name = "user_info")

suspend fun saveUserInfo(context: Context, age: Int, gender: String, consent: Boolean) {
    context.userDataStore.edit { preferences ->
        preferences[UserMetadataKeys.AGE] = age
        preferences[UserMetadataKeys.GENDER] = gender
        preferences[UserMetadataKeys.CONSENT] = consent
    }
}

suspend fun loadUserInfo(context: Context): Triple<Int?, String, Boolean> {
    val preferences = context.userDataStore.data.first()
    val age = preferences[UserMetadataKeys.AGE]
    val gender = preferences[UserMetadataKeys.GENDER] ?: ""
    val consent = preferences[UserMetadataKeys.CONSENT] == true
    return Triple(age, gender, consent)
}