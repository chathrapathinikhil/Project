package edu.fullerton.jd.workodoro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.fullerton.jd.workodoro.SQLite.DatabaseRepository
import edu.fullerton.jd.workodoro.SQLite.Task

private const val TAG = "Fragment3"

class Fragment3 : Fragment()
{
    private lateinit var taskText: TextView
    private lateinit var repository: DatabaseRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        Log.v(TAG, "Task fragment: onCreateView()")
        val view = inflater.inflate(R.layout.fragment3, container, false)

        // bind views to variables here
        taskText = view.findViewById(R.id.taskText)

        //repository = DatabaseRepository(this) // TODO causes error

        taskText = view.findViewById(R.id.taskText)

        //setupObservers() // TODO causes error

        return view
    }

    private fun viewsToTask(): Task
    {
        val task = Task()
        task.task = taskText.text.toString()

        Log.v(TAG, "viewsToTask() - $task")

        return task
    }

    private fun taskToViews(task: Task?)
    {
        if (task == null)
        {
            taskText.setText("")
        }
        else
        {
            taskText.setText(task.task)
        }
    }

    private fun setupObservers()
    {
        repository.currentTaskID.observe(viewLifecycleOwner) { idq ->

            Log.v(TAG, "The current task ID has changed: $idq")

            if (idq == null)
            {
                taskToViews(null)
            }

            idq?.let { id ->
                val taskLiveData = repository.fetchTask(id)
                taskLiveData.observe(viewLifecycleOwner) { task ->

                    Log.v(TAG,"New task: $task")

                    taskToViews(task)
                }
            }
        }
    }
}

