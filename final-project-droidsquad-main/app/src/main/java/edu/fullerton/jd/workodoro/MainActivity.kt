package edu.fullerton.jd.workodoro

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    // fileStorage class
    private val fileStorage = FileStorage(this)

    // ViewModel class
    private val valuesViewModel: ViewModel by lazy {
        ViewModelProvider(this)[ViewModel::class.java]
    }

    // view variables
    private var switch: Switch? = null // not in landscape mode
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesRepository.initialize(this)
        setContentView(R.layout.activity_main)

        // nullable switch
        switch = findViewById(R.id.switch1)

        // load the switch status from a file to the ViewModel on creation
        valuesViewModel.setSwitchStatus(fileStorage.loadSwitchStatus())
        switch?.isChecked = valuesViewModel.getSwitchStatus()

        // fragment frames
        val fragment1 = supportFragmentManager.findFragmentById(R.id.frame_for_fragment1)
        val fragment2 = supportFragmentManager.findFragmentById(R.id.frame_for_fragment2)
        val fragment3 = supportFragmentManager.findFragmentById(R.id.frame_for_fragment3)

        // fragment classes
        val frag1 = Fragment1()
        val frag2 = BlankFragment4()
        val frag3 = Fragment3()

//        if (fragment2 == null) {
////            val frag = Fragment2()
//            val frag = BlankFragment4()
//            this.supportFragmentManager
//                .beginTransaction()
//                .add(R.id.frame_for_fragment2, frag)
//                .commit()
//        }

        switch?.setOnClickListener {
            // get the status from the file and save it to the ViewModel
            valuesViewModel.setSwitchStatus(switch!!.isChecked)
            fileStorage.saveSwitchStatus(valuesViewModel.getSwitchStatus())

            if (valuesViewModel.getSwitchStatus()) // if the switch status is true, remove fragment 2
                supportFragmentManager
                    .beginTransaction()
                    .remove(frag2)
                    .commit()
            else // add fragment 2
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_for_fragment2, frag2)
                    .commit()

            Log.v(TAG, "Switch clicked")
        }

        // create initial fragments
        if (fragment1 == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_for_fragment1, frag1)
                .commit()

        // only add fragment 2 if the phone is in landscape OR if the switch is not checked
        if (fragment2 == null && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE || !valuesViewModel.getSwitchStatus())
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_for_fragment2, frag2)
                    .commit()

        // only add fragment 3 if the phone is in portrait
//        if (fragment3 == null && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.frame_for_fragment3, frag3)
//                .commit() TODO would be used for the SQLite database

        Log.i(TAG, "onCreate() called")
    }

    override fun onPause()
    {
        super.onPause()

        // get a list of all fragments and remove each one
        val fragmentList = supportFragmentManager.fragments
        if (fragmentList.isNotEmpty())
            for (fragment in fragmentList)
                supportFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit()

        Log.i(TAG, "onPause() called")
    }
}