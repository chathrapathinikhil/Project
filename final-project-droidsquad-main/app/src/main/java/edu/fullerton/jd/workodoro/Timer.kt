package edu.fullerton.jd.workodoro

class Timer {

    var hourToCount: Int = 0
    var minToCount: Int = 0
    var secondToCount: Int = 0

    private var isCounting = false
    var isPause = false

    private var workState = WorkState.Work

    private var shortBreakTimer: Int = 5
    private var longBreakTimer: Int = 15
    private var workTimer: Int = 25

    fun loadShortBreakTimer(){

        hourToCount = 0
        minToCount = shortBreakTimer
        secondToCount = 0
    }

    fun loadLongBreakTimer(){

        hourToCount = 0
        minToCount = longBreakTimer
        secondToCount = 0
    }

    fun loadWorkTimer(){
        hourToCount = 0
        minToCount = workTimer
        secondToCount = 0
    }

    fun displayTime(): String{
        var result = ""

        val hr = hourToCount.toString()
        val min = minToCount.toString()
        val sec = secondToCount.toString()

        if (hourToCount>0){
            result += (if(hr.length<2)hr.padStart(2,'0')else hr) + ":"
        }

        result += (if(min.length<2)min.padStart(2,'0')else min) + ":"
        result += (if (sec.length<2)sec.padStart(2,'0')else sec)
        return result
    }

    fun toSeconds(): Long{
        return hourToCount.toLong() * 60 * 60 + minToCount.toLong() * 60 + secondToCount.toLong()
    }

    fun restoreFromSeconds(s:Long){
        secondToCount = (s % 60).toInt()
        minToCount = (((s-secondToCount)/60) % 60).toInt()
        hourToCount = ((s-secondToCount-minToCount*60)/3600).toInt()
    }

    fun minusToSecond(){
        if (hourToCount == 0 && minToCount == 0 && secondToCount <= 1){
            secondToCount = 0
            isCounting = false
            return
        }

        if (secondToCount == 0 && minToCount > 0) {
            secondToCount = 60
            minToCount--
        }
        else if (secondToCount == 0 && minToCount == 0){
            secondToCount = 60
            minToCount = 60
            hourToCount--
        }
        secondToCount--

    }

//    fun resetTimer(){
//        isCounting = false
//        isPause = false
//        workState = WorkState.Work
//
//    }


}