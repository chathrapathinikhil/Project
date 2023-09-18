package edu.fullerton.jd.workodoro.SQLite

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.UUID

@Dao // Data Access Object
interface DataAccessObject
{
    @Query("SELECT `id` FROM TASK ORDER BY `task` ASC")
    fun fetchTaskIDs(): LiveData<List<UUID>>

    @Query("SELECT * FROM TASK")
    fun fetchTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM TASK WHERE id=(:id)")
    fun fetchTask(id: UUID): LiveData<Task?>

    @Insert
    fun addTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("DELETE FROM TASK WHERE id=(:id)")
    fun removeTask(id: UUID)
}
