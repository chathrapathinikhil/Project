package edu.fullerton.jd.workodoro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Define a class to represent the data you want to store
data class MyData(val id: Int, val name: String, val value: Double)

// Define a contract class to specify the schema of your database
object MyDataContract {
    const val TABLE_NAME = "my_data"
    const val COLUMN_ID = "_id"
    const val COLUMN_NAME = "name"
    const val COLUMN_VALUE = "value"

    const val SQL_CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_VALUE REAL)"

    const val SQL_DROP_TABLE =
        "DROP TABLE IF EXISTS $TABLE_NAME"
}

// Define a helper class to manage the database
class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "my_database", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MyDataContract.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(MyDataContract.SQL_DROP_TABLE)
        onCreate(db)
    }

    fun insertData(data: MyData) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(MyDataContract.COLUMN_ID, data.id)
            put(MyDataContract.COLUMN_NAME, data.name)
            put(MyDataContract.COLUMN_VALUE, data.value)
        }
        db.insert(MyDataContract.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllData(): List<MyData> {
        val db = readableDatabase
        val cursor = db.query(
            MyDataContract.TABLE_NAME,
            arrayOf(MyDataContract.COLUMN_ID, MyDataContract.COLUMN_NAME, MyDataContract.COLUMN_VALUE),
            null, null, null, null, null
        )
        val dataList = mutableListOf<MyData>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(MyDataContract.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(MyDataContract.COLUMN_NAME))
                val value = getDouble(getColumnIndexOrThrow(MyDataContract.COLUMN_VALUE))
                dataList.add(MyData(id, name, value))
            }
        }
        cursor.close()
        db.close()
        return dataList
    }
}
