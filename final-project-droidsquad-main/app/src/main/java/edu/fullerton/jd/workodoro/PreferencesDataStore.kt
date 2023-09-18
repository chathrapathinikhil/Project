package edu.fullerton.jd.workodoro

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
//import java.util.prefs.Preferences

//we'll add one to hold the cycle, the skip counter, and the timer itself
//
class PreferencesRepository private constructor(private val dataStore: DataStore<Preferences>){

    //to remember our time!!!!
    private val HOUR_VAL = intPreferencesKey("hour_value")
    private val MINUTE_VAL = intPreferencesKey("minute_value")
    private val SECOND_VAL = intPreferencesKey("second_value")

    //probably required to stay on track with the timer val
    private val SKIP_VAL = intPreferencesKey("skip_value")

    //to keep track of which cycle we're at
    private val CYCLE_VAL = intPreferencesKey("cycle_value")

    val hour_value: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[HOUR_VAL] ?: INITIAL_HOUR_VALUE
    }.distinctUntilChanged()

    val minute_value: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[MINUTE_VAL] ?: INITIAL_MINUTE_VALUE
    }.distinctUntilChanged()

    val second_value: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[SECOND_VAL] ?: INITIAL_SECOND_VALUE
    }.distinctUntilChanged()

    val skip_value: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[SKIP_VAL] ?: INITIAL_SKIP_VALUE
    }.distinctUntilChanged()

    val cycle_value: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[CYCLE_VAL] ?: INITIAL_CYCLE_VALUE
    }.distinctUntilChanged()

    private suspend fun saveStringValue(key: Preferences.Key<String>, value: String) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    private suspend fun saveIntValue(key: Preferences.Key<Int>, value: Int) {
        this.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    suspend fun saveHourValue(value: Int) {
        saveIntValue(HOUR_VAL, value)
    }

    suspend fun saveMinuteValue(value: Int) {
        saveIntValue(MINUTE_VAL, value)
    }

    suspend fun saveSecondValue(value: Int) {
        saveIntValue(SECOND_VAL, value)
    }
    suspend fun saveSkipValue(value: Int) {
        saveIntValue(SKIP_VAL, value)
    }

    suspend fun saveCycleValue(value: Int) {
        saveIntValue(CYCLE_VAL, value)
    }

    companion object {
        private const val PREFERENCES_DATA_FILE_NAME = "settings"
        private var INSTANCE: PreferencesRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                INSTANCE = PreferencesRepository(dataStore)
            }
        }

        fun getRepository(): PreferencesRepository {
            return INSTANCE ?: throw IllegalStateException("PreferencesRepository not initialized yet")
        }
    }
}