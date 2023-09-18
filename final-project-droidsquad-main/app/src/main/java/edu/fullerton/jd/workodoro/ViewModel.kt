package edu.fullerton.jd.workodoro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

private const val TAG = "ViewModel"
const val INITIAL_SWITCH_STATUS = false
const val INITIAL_HOUR_VALUE = 0
const val INITIAL_MINUTE_VALUE = 0
const val INITIAL_SECOND_VALUE = 0
const val INITIAL_CYCLE_VALUE = 1
const val INITIAL_SKIP_VALUE = 0

class ViewModel : ViewModel()
{

    private var switchStatus = INITIAL_SWITCH_STATUS

    // variables go here

    private var hourValue: Int = INITIAL_HOUR_VALUE
    private var minuteValue: Int = INITIAL_MINUTE_VALUE
    private var secondValue: Int = INITIAL_SECOND_VALUE
    private var cycleValue: Int = INITIAL_CYCLE_VALUE
    private var skipValue: Int = INITIAL_SKIP_VALUE

    override fun onCleared() {
        super.onCleared()
        Log.d("MainActivity", "OnCleared of CounterViewActivity called")
    }

    private val prefs = PreferencesRepository.getRepository()

    fun getSwitchStatus(): Boolean
    {
        Log.v(TAG, "getSwitchStatus() called")
        Log.d(TAG, "Getting switch status: $switchStatus")
        return switchStatus
    }

    fun setSwitchStatus(status: Boolean)
    {
        Log.v(TAG, "setSwitchStatus() called")
        Log.d(TAG, "Setting switch status: $status")
        switchStatus = status
    }

    fun saveHourVal() {
        viewModelScope.launch {
            prefs.saveHourValue(hourValue)
            Log.d(TAG, "Saving the hour value: $hourValue")
        }
    }

    fun saveMinuteVal() {
        viewModelScope.launch {
            prefs.saveMinuteValue(minuteValue)
            Log.d(TAG, "Saving the minute value: $minuteValue")
        }
    }

    fun saveSecondVal() {
        viewModelScope.launch {
            prefs.saveSecondValue(secondValue)
            Log.d(TAG, "Saving the second value: $secondValue")
        }
    }
    fun saveSkipVal() {
        viewModelScope.launch {
            prefs.saveSkipValue(skipValue)
            Log.d(TAG, "Saving the skip value: $skipValue")
        }
    }

    fun saveCycleVal() {
        viewModelScope.launch {
            prefs.saveCycleValue(cycleValue)
            Log.d(TAG, "Saving the cycle value: $cycleValue")
        }
    }

    fun loadHourVal() {
        GlobalScope.launch {
            prefs.hour_value.collectLatest {
                hourValue = it
                Log.d(TAG, "Loaded the hour_value from DataStore: $hourValue")
            }
        }
        sleep(250)
    }

    fun loadMinuteVal() {
        GlobalScope.launch {
            prefs.minute_value.collectLatest {
                minuteValue = it
                Log.d(TAG, "Loaded the minute_value from DataStore: $minuteValue")
            }
        }
        sleep(250)
    }

    fun loadSecondVal() {
        GlobalScope.launch {
            prefs.second_value.collectLatest {
                secondValue = it
                Log.d(TAG, "Loaded the second_value from DataStore: $secondValue")
            }
        }
        sleep(250)
    }

    fun loadSkipVal() {
        GlobalScope.launch {
            prefs.skip_value.collectLatest {
                skipValue = it
                Log.d(TAG, "Loaded the red_value from DataStore: $skipValue")
            }
        }
        sleep(250)
    }

    fun loadCycleVal() {
        GlobalScope.launch {
            prefs.cycle_value.collectLatest {
                cycleValue = it
                Log.d(TAG, "Loaded the red_value from DataStore: $cycleValue")
            }
        }
        sleep(250)
    }

    fun getHourVal(): Int {
        return this.hourValue
    }

    fun getMinuteVal(): Int {
        return this.minuteValue
    }

    fun getSecondVal(): Int {
        return this.secondValue
    }

    fun getSkipVal(): Int {
        return this.skipValue
    }

    fun getCycleVal(): Int {
        return this.cycleValue
    }

    fun setHourVal(c: Int) {
        this.hourValue = c
        saveHourVal()
    }

    fun setMinuteVal(c: Int) {
        this.minuteValue = c
        saveMinuteVal()
    }

    fun setSecondVal(c: Int) {
        this.secondValue = c
        saveSecondVal()
    }

    fun setCycleVal(c: Int) {
        this.cycleValue = c
        saveCycleVal()
    }

    fun setSkipVal(c: Int) {
        this.skipValue = c
        saveSkipVal()
    }

}