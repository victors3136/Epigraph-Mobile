package ubb.victors3136.epigraphmobile.persistance

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

object UserMetadataKeys {
    val AGE = intPreferencesKey("user_age")
    val GENDER = stringPreferencesKey("user_gender")
    val CONSENT = booleanPreferencesKey("user_consent")
}

suspend fun saveUserInfo(
    dataStore: DataStore<Preferences>,
    age: Int,
    gender: String,
    consent: Boolean,
) {
    dataStore.edit { preferences ->
        preferences[UserMetadataKeys.AGE] = age
        preferences[UserMetadataKeys.GENDER] = gender
        preferences[UserMetadataKeys.CONSENT] = consent
    }
}

suspend fun loadUserInfo(dataStore: DataStore<Preferences>): Triple<Int?, String, Boolean> {
    val preferences = dataStore.data.first()
    val age = preferences[UserMetadataKeys.AGE]
    val gender = preferences[UserMetadataKeys.GENDER] ?: ""
    val consent = preferences[UserMetadataKeys.CONSENT] == true
    return Triple(age, gender, consent)
}
