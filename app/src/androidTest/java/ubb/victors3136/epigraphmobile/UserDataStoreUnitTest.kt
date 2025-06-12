package ubb.victors3136.epigraphmobile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ubb.victors3136.epigraphmobile.persistance.UserMetadataKeys
import ubb.victors3136.epigraphmobile.persistance.loadUserInfo
import ubb.victors3136.epigraphmobile.persistance.saveUserInfo
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class UserInfoUnitTest {

    private lateinit var testDataStoreFile: File
    private lateinit var dataStore: DataStore<Preferences>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var testScope: CoroutineScope

    @Before
    fun setup() {
        testDataStoreFile = File(context.filesDir, "test_user_info.preferences_pb").also {
            if (it.exists()) it.delete()
        }

        testScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        dataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { testDataStoreFile }
        )
    }

    @After
    fun cleanup() = runBlocking {
        testScope.cancel()
        delay(100)
        if (testDataStoreFile.exists()) testDataStoreFile.delete()
    }

    @Test
    fun testPositiveAgeSavedCorrectly() = runTest {
        saveUserInfo(dataStore, age = 42, gender = "", consent = false)
        val preferences = dataStore.data.first()
        assertEquals(42, preferences[UserMetadataKeys.AGE])
    }

    @Test
    fun testZeroAgeSavedCorrectly() = runTest {
        saveUserInfo(dataStore, age = 0, gender = "", consent = false)
        val preferences = dataStore.data.first()
        assertEquals(0, preferences[UserMetadataKeys.AGE])
    }


    @Test
    fun testEmptyGenderSavedCorrectly() = runTest {
        saveUserInfo(dataStore, age = 0, gender = "", consent = false)
        val preferences = dataStore.data.first()
        assertEquals("", preferences[UserMetadataKeys.GENDER])
    }
    @Test
    fun testGenderSavedCorrectly() = runTest {
        saveUserInfo(dataStore, age = 0, gender = "Other", consent = false)
        val preferences = dataStore.data.first()
        assertEquals("Other", preferences[UserMetadataKeys.GENDER])
    }
    @Test
    fun testPositiveConsentSavedCorrectly() = runTest {
        saveUserInfo(dataStore, age = 0, gender = "", consent = true)
        val preferences = dataStore.data.first()
        assertEquals(true, preferences[UserMetadataKeys.CONSENT])
    }
    @Test
    fun testNegativeConsentSavedCorrectly() = runTest {
        saveUserInfo(dataStore, age = 0, gender = "Other", consent = false)
        val preferences = dataStore.data.first()
        assertEquals(false, preferences[UserMetadataKeys.CONSENT])
    }

    @Test
    fun testPositiveAgeLoadedCorrectly() = runTest {
        dataStore.edit { prefs ->
            prefs[UserMetadataKeys.AGE] = 25
        }

        val (age, _, _) = loadUserInfo(dataStore)
        assertEquals(25, age)
    }

    @Test
    fun testZeroAgeLoadedCorrectly() = runTest {
        dataStore.edit { prefs ->
            prefs[UserMetadataKeys.AGE] = 0
        }

        val (age, _, _) = loadUserInfo(dataStore)
        assertEquals(0, age)
    }


    @Test
    fun testGenderLoadedCorrectly() = runTest {
        dataStore.edit { prefs ->
            prefs[UserMetadataKeys.GENDER] = "Female"
        }

        val (_, gender, _) = loadUserInfo(dataStore)
        assertEquals("Female", gender)
    }

    @Test
    fun testEmptyGenderLoadedCorrectly() = runTest {
        dataStore.edit { prefs ->
            prefs[UserMetadataKeys.GENDER] = ""
        }

        val (_, gender, _) = loadUserInfo(dataStore)
        assertEquals("", gender)
    }

    @Test
    fun testPositiveConsentLoadedCorrectly() = runTest {
        dataStore.edit { prefs ->
            prefs[UserMetadataKeys.CONSENT] = true
        }

        val (_, _, consent) = loadUserInfo(dataStore)
        assertTrue(consent)
    }
    @Test
    fun testNegativeConsentLoadedCorrectly() = runTest {
        dataStore.edit { prefs ->
            prefs[UserMetadataKeys.CONSENT] = false
        }

        val (_, _, consent) = loadUserInfo(dataStore)
        assertTrue(!consent)
    }
}
