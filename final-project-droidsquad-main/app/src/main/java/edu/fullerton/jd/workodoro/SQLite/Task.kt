package edu.fullerton.jd.workodoro.SQLite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "TASK")
data class Task(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        var id: UUID = UUID.randomUUID(),

        @ColumnInfo(name = "task")
        var task: String = "",
)
