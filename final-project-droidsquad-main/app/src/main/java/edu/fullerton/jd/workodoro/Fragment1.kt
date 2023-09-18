package edu.fullerton.jd.workodoro

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

const val LOG_TAG = "Fragment 1"

const val TIMER_VAL = "timer_value"
const val CYCLE_VAL = "cycle_value"
const val SKIP_VAL = "skip_value"

class Fragment1 : Fragment()
{
    private lateinit var text: TextView
    private lateinit var timerView: TextView
    private lateinit var cycleView: TextView

    //start and pause button
    private lateinit var relaxButton: Button
    private var timer = Timer()
    private var timerJob: Job? = null

    private lateinit var skipButton: Button
    private var skipCounter = 0

    private lateinit var focusButton: Button
    private lateinit var shortButton: Button
    private lateinit var longButton: Button

    private lateinit var resetButton: Button

    private lateinit var timerBackground: LinearLayout
    private lateinit var mainBackground: LinearLayout

    private var timerType = "work"
    //will help us keep track of which cycle we're on
    private var cycleCounter = 1

    private var pressedStart = 0

    private fun resetTimerState(type: String) {
        timerJob?.cancel()
        timer.isPause = false
        relaxButton.text = getString(R.string.start)


        when (type) {
            "work" -> timer.loadWorkTimer()
            "short_break" -> timer.loadShortBreakTimer()
            "long_break" -> timer.loadLongBreakTimer()
        }

        timerType = type
        timerView.text = timer.displayTime()
    }

    @SuppressLint("ResourceType")
    private fun setTimerBackground(linearLayout: LinearLayout, resource: Int) {
        linearLayout.setBackgroundResource(resource)
    }

    private fun setColorScheme(btnClr: Int, txtClr: Int) {
//        mainBackground.setBackgroundResource()
        val btnColor = ContextCompat.getColorStateList(requireContext(), btnClr)
        val txtColor = ContextCompat.getColorStateList(requireContext(), txtClr)

        focusButton.backgroundTintList = btnColor
        focusButton.setTextColor(txtColor)

        shortButton.backgroundTintList = btnColor
        shortButton.setTextColor(txtColor)

        longButton.backgroundTintList = btnColor
        longButton.setTextColor(txtColor)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val valuesViewModel = activity?.let {
            ViewModelProvider(it)[ViewModel::class.java]
        } ?: throw Exception("Activity is null")


        // view for fragment1
        val view = inflater.inflate(R.layout.fragment1, container, false)
        timerView = view.findViewById(R.id.timerView)
        cycleView = view.findViewById(R.id.cycleCount)
        relaxButton = view.findViewById(R.id.relaxButton)
        skipButton = view.findViewById(R.id.skipButton)
        focusButton = view.findViewById(R.id.focusButton)
        shortButton = view.findViewById(R.id.shortButton)
        longButton = view.findViewById(R.id.longButton)

        timerBackground = view.findViewById(R.id.timerLayout)
        mainBackground = view.findViewById(R.id.ip)
        resetButton = view.findViewById(R.id.resetButton)

        //initial call for when nothing is stored in the data store yet
        //gets changed later when the other values get loaded in
        timer.loadWorkTimer()
        timer.displayTime()

        resetButton.setOnClickListener {
            cycleCounter = 1
            skipCounter = 0

            valuesViewModel.setSkipVal(skipCounter)
            valuesViewModel.saveSkipVal()

            valuesViewModel.setCycleVal(cycleCounter)
            valuesViewModel.saveCycleVal()

            resetTimerState("work")

            valuesViewModel.setHourVal(timer.hourToCount)
            valuesViewModel.saveHourVal()

            valuesViewModel.setMinuteVal(timer.minToCount)
            valuesViewModel.saveMinuteVal()

            valuesViewModel.setSecondVal(timer.secondToCount)
            valuesViewModel.saveSecondVal()

            cycleView.text = getString(R.string.cycle) + cycleCounter
        }
        focusButton.setOnClickListener {
            resetTimerState("work")
            skipCounter = 0
            pressedStart = 0
            setTimerBackground(timerBackground, R.raw.studying_img)
            setColorScheme(R.color.transparent_white, R.color.black)

            valuesViewModel.setSkipVal(skipCounter)
            valuesViewModel.saveSkipVal()

            valuesViewModel.setHourVal(timer.hourToCount)
            valuesViewModel.saveHourVal()

            valuesViewModel.setMinuteVal(timer.minToCount)
            valuesViewModel.saveMinuteVal()

            valuesViewModel.setSecondVal(timer.secondToCount)
            valuesViewModel.saveSecondVal()
        }

        shortButton.setOnClickListener {
            resetTimerState("short_break")
            skipCounter = 1
            pressedStart = 0
            setTimerBackground(timerBackground, R.raw.mountain_clouds)
            setColorScheme(R.color.transparent_blk, R.color.white)
            valuesViewModel.setSkipVal(skipCounter)
            valuesViewModel.saveSkipVal()

            valuesViewModel.setHourVal(timer.hourToCount)
            valuesViewModel.saveHourVal()

            valuesViewModel.setMinuteVal(timer.minToCount)
            valuesViewModel.saveMinuteVal()

            valuesViewModel.setSecondVal(timer.secondToCount)
            valuesViewModel.saveSecondVal()
        }

        longButton.setOnClickListener {
            resetTimerState("long_break")
            skipCounter = -1
            pressedStart = 0
            valuesViewModel.setSkipVal(skipCounter)
            valuesViewModel.saveSkipVal()

            valuesViewModel.setHourVal(timer.hourToCount)
            valuesViewModel.saveHourVal()

            valuesViewModel.setMinuteVal(timer.minToCount)
            valuesViewModel.saveMinuteVal()

            valuesViewModel.setSecondVal(timer.secondToCount)
            valuesViewModel.saveSecondVal()
            setTimerBackground(timerBackground, R.raw.bright_beach_relaxing_img)
            setColorScheme(R.color.transparent_dark_blue, R.color.white)
        }

        skipButton.setOnClickListener {
            pressedStart = 0
            skipCounter++
            valuesViewModel.setSkipVal(skipCounter)
            valuesViewModel.saveSkipVal()
            timer.isPause = false;

            Log.d(LOG_TAG, "Skip Counter: $skipCounter")

            if (skipCounter % 2 == 1 && skipCounter != 7) {
                resetTimerState("short_break")

                setTimerBackground(timerBackground, R.raw.mountain_clouds)
                setColorScheme(R.color.transparent_blk, R.color.white)
            } else if (skipCounter % 2 == 0){
                resetTimerState("work")

                setTimerBackground(timerBackground, R.raw.studying_img)
                setColorScheme(R.color.transparent_white, R.color.black)
            }
            else if (skipCounter % 2 == 1 && skipCounter == 7){
                resetTimerState("long_break")
                skipCounter = -1
                valuesViewModel.setSkipVal(skipCounter)
                valuesViewModel.saveSkipVal()
                cycleCounter++
                valuesViewModel.setCycleVal(cycleCounter)
                valuesViewModel.saveCycleVal()
                cycleView.text = getString(R.string.cycle) + cycleCounter

                setTimerBackground(timerBackground, R.raw.bright_beach_relaxing_img)
                setColorScheme(R.color.transparent_dark_blue, R.color.white)

            }

            valuesViewModel.setHourVal(timer.hourToCount)
            valuesViewModel.saveHourVal()

            valuesViewModel.setMinuteVal(timer.minToCount)
            valuesViewModel.saveMinuteVal()

            valuesViewModel.setSecondVal(timer.secondToCount)
            valuesViewModel.saveSecondVal()
        }

        relaxButton.setOnClickListener {
            cycleView.text = getString(R.string.cycle) + cycleCounter
            if (!timer.isPause && pressedStart != 1) {
                relaxButton.text = getString(R.string.pause)
                relaxButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause, 0, 0, 0)
                timer.isPause = true

//                if(timerType == "work") {
//                    timer.loadWorkTimer()
//                }
//                else if (timerType =="short_break") {
//                    timer.loadShortBreakTimer()
//                }
//                else if (timerType == "long break") {
//                    timer.loadLongBreakTimer()
//                }

                Log.d(LOG_TAG, "This ran: ${timer.isPause}")

                timerJob = GlobalScope.launch {
                    while (timer.isPause) {
                        delay(1000)
                        timer.minusToSecond()

                        valuesViewModel.setHourVal(timer.hourToCount)
                        valuesViewModel.saveHourVal()

                        valuesViewModel.setMinuteVal(timer.minToCount)
                        valuesViewModel.saveMinuteVal()

                        valuesViewModel.setSecondVal(timer.secondToCount)
                        valuesViewModel.saveSecondVal()

                        withContext(Dispatchers.Main) {
                            timerView.text = timer.displayTime()
                            Log.d(LOG_TAG, "timerView should be: ${timerView.text}")
                            if(timerView.text == "00:00") {
                                Log.d(LOG_TAG, "timerView has reached: ${timerView.text}")
                                skipButton.callOnClick()
                            }
                        }
                    }
                }

                pressedStart++;
            }
            else if (pressedStart == 1){
                //if timer.isPause is set to false
                if(!timer.isPause) {
                    relaxButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause, 0, 0, 0)
                    timer.isPause = true

                    relaxButton.text = getString(R.string.pause)


                    timerJob = GlobalScope.launch {
                        while (timer.isPause) {
                            delay(1000)
                            timer.minusToSecond()

                            valuesViewModel.setHourVal(timer.hourToCount)
                            valuesViewModel.saveHourVal()

                            valuesViewModel.setMinuteVal(timer.minToCount)
                            valuesViewModel.saveMinuteVal()

                            valuesViewModel.setSecondVal(timer.secondToCount)
                            valuesViewModel.saveSecondVal()

                            withContext(Dispatchers.Main) {
                                timerView.text = timer.displayTime()
                                if(timerView.text == "00:00") {
                                    Log.d(LOG_TAG, "timerView has reached: ${timerView.text}")
                                    skipButton.callOnClick()
                                }
                            }
                        }
                    }

                }
                else {
                    relaxButton.text = getString(R.string.resume)
                    relaxButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0, 0, 0)

                    timer.isPause = false
                }

            }
        }

        valuesViewModel.loadCycleVal()
        valuesViewModel.loadSkipVal()

        valuesViewModel.loadHourVal()
        valuesViewModel.loadMinuteVal()
        valuesViewModel.loadSecondVal()

        Log.d(LOG_TAG, "setting cycle value after loading it from DataStore to ${valuesViewModel.getCycleVal()}")
        Log.d(LOG_TAG, "setting skip value after loading it from DataStore to ${valuesViewModel.getSkipVal()}")

        Log.d(LOG_TAG, "setting hour value after loading it from DataStore to ${valuesViewModel.getHourVal()}")
        Log.d(LOG_TAG, "setting minute value after loading it from DataStore to ${valuesViewModel.getMinuteVal()}")
        Log.d(LOG_TAG, "setting second value after loading it from DataStore to ${valuesViewModel.getSecondVal()}")

        cycleCounter = valuesViewModel.getCycleVal()
        skipCounter = valuesViewModel.getSkipVal()

        timer.hourToCount = valuesViewModel.getHourVal()
        timer.minToCount = valuesViewModel.getMinuteVal()
        timer.secondToCount = valuesViewModel.getSecondVal()

        timerView.text = timer.displayTime()
        cycleView.text = getString(R.string.cycle) + cycleCounter


        return view
    }
}