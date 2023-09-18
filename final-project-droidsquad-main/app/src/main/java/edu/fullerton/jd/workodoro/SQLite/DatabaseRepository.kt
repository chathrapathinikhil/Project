package edu.fullerton.jd.workodoro.SQLite

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "DatabaseRepository"
private const val DATABASE_NAME = "tasks-database"

class DatabaseRepository constructor(private val fragment: Fragment)
{
    private val database: Database = Room.databaseBuilder(
        fragment.requireContext().applicationContext,
        Database::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    private val dataAccessObject = database.myDao()
    private val executor = Executors.newSingleThreadExecutor()

    // task IDs
    private var currentTaskIndex: Int = 0
    var currentTaskID: MutableLiveData<UUID?> = MutableLiveData<UUID?>()
    val taskIDs: LiveData<List<UUID>> = this.fetchTaskIDs()
    var currentTask: LiveData<Task?> = MutableLiveData<Task?>()

    init
    {
        watchStuff()
    }

    private fun watchStuff()
    {
        Log.v(TAG, "watchTaskIDs()")

        taskIDs.observe(fragment.viewLifecycleOwner) { ids ->
            Log.v(TAG, "Loaded task IDs: $ids")
            updateCurrentTaskID()
            updateCurrentTask()
        }

        currentTask.observe(fragment.viewLifecycleOwner) { task ->
            Log.v(TAG, "Loaded task: $task")
        }
    }

    private fun keepCurrentTaskIndexInBounds()
    {
        Log.v(TAG, "keepCurrentTaskIndexInBounds() - Start value: $currentTaskIndex")

        if (taskIDs.value == null)
        {
            currentTaskIndex = 0
        }
        else
        {
            val ids = taskIDs.value
            if (currentTaskIndex < 0)
            {
                currentTaskIndex = ids!!.size - 1
            }
            else if (currentTaskIndex >= ids!!.size)
            {
                currentTaskIndex = 0
            }
        }

        Log.v(TAG, "keepCurrentTaskIndexInBounds() - End value: $currentTaskIndex")
    }

    fun previousTask(): Unit
    {
        adjustTaskIndex(-1)
    }
    fun nextTask(): Unit
    {
        adjustTaskIndex(1)
    }
    private fun adjustTaskIndex(adjustment: Int)
    {
        currentTaskIndex += adjustment
        keepCurrentTaskIndexInBounds()
        updateCurrentTaskID()
    }

    fun updateCurrentTaskID()
    {
        Log.v(TAG, "updateCurrentTaskID()")

        keepCurrentTaskIndexInBounds()

        taskIDs.value?.let {
            if (currentTaskIndex >= 0 && currentTaskIndex < it.size )
            {
                currentTaskID.value = it[currentTaskIndex]
                updateCurrentTask()
            }
        }
    }

    fun updateCurrentTask()
    {
        Log.v(TAG, "updateCurrentTask()")

        currentTaskID.value?.let { pid ->
            currentTask = dataAccessObject.fetchTask(pid)
            currentTask.observe(fragment.viewLifecycleOwner) { task ->
                Log.v(TAG, "Loaded task: $task")
            }
        }
    }

    fun fetchTaskIDs(): LiveData<List<UUID>> = dataAccessObject.fetchTaskIDs()
    fun fetchTasks(): LiveData<List<Task>> = dataAccessObject.fetchTasks()
    fun fetchTask(id: UUID): LiveData<Task?> = dataAccessObject.fetchTask(id)

    fun addTask(task: Task)
    {
        executor.execute {
            dataAccessObject.addTask(task)
        }
    }
    fun updateTask(task: Task)
    {
        executor.execute {
            dataAccessObject.updateTask(task = task)
        }
    }
    fun removeTask(id: UUID)
    {
        executor.execute {
            dataAccessObject.removeTask(id)
        }
    }
}