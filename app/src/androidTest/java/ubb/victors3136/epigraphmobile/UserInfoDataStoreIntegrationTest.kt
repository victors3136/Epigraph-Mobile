package ubb.victors3136.epigraphmobile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ubb.victors3136.epigraphmobile.persistance.loadUserInfo
import ubb.victors3136.epigraphmobile.persistance.saveUserInfo
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class UserInfoIntegrationTest {

    private lateinit var testDataStoreFile: File
    private lateinit var dataStore: DataStore<Preferences>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var testScope: CoroutineScope

    @Before
    fun setup() {
        testDataStoreFile =
            File(context.filesDir, "test_user_info_integration.preferences_pb").also {
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
    fun testSaveAndLoadUserInfo() = runTest {
        saveUserInfo(dataStore, age = 30, gender = "Non-binary", consent = true)

        val (age, gender, consent) = loadUserInfo(dataStore)

        assertEquals(30, age)
        assertEquals("Non-binary", gender)
        assertTrue(consent)
    }

    @Test
    fun testOverwriteUserInfo() = runTest {
        saveUserInfo(dataStore, age = 20, gender = "Male", consent = false)

        saveUserInfo(dataStore, age = 45, gender = "Female", consent = true)

        val (age, gender, consent) = loadUserInfo(dataStore)

        assertEquals(45, age)
        assertEquals("Female", gender)
        assertTrue(consent)
    }

    @Test
    fun testLoadUserInfoDefaultsWhenEmpty() = runTest {
        val (age, gender, consent) = loadUserInfo(dataStore)

        assertNull(age)
        assertEquals("", gender)
        assertFalse(consent)
    }
}
