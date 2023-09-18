package edu.fullerton.jd.workodoro

import android.content.Context
import android.util.Log
import java.io.File

private const val TAG = "FileStorage"

/*
Steps to save a value to a file:
1. Declare a new file name
   Ex. private val <file> = "<file>.txt"
2. Define a new function for saving the value
   Ex. fun saveValue(value: <data type>)
       {
           val file = makeFile(<storage file>)
           file.delete()
           file.writeText(value[.toString()])
       }
3. Define a new function for loading the value
   Ex. fun loadValue(): <data type>
       {
           val file = makeFile(<storage file>)
           return if (file.exists())
               file.readText()[.to<data type>]
           else
               <default value>
       }
4. Call .saveValue(...) and .loadValue(...) from main when needed
*/
class FileStorage(context: Context)
{
    // context passed from MainActivity
    private var context = context
    private val switchStatusFileName = "switchStatus.txt"

    // create file in the context's directory and return a handle
    private fun makeFile(fileName: String): File
    {
        return File(context.filesDir, fileName)
    }

    fun saveSwitchStatus(status: Boolean)
    {
        val file = makeFile(switchStatusFileName)
        file.delete()
        file.writeText(status.toString())
        Log.d(TAG, "Writing switch status: $status")
    }

    fun loadSwitchStatus(): Boolean
    {
        val file = makeFile(switchStatusFileName)

        return if (file.exists())
        {
            val status = file.readText().toBoolean()
            Log.d(TAG, "Read switch status: $status")

            status
        }
        else
            false
    }
}