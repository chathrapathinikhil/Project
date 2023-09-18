package edu.fullerton.jd.workodoro.SQLite

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Task::class],
    version=4
)
@TypeConverters(TypeConverters::class)
abstract class Database : RoomDatabase()
{
    abstract fun myDao(): DataAccessObject
}

