package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.database.Cursor

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DatabaseHelper", "Creating database and tables...")
        db.execSQL(UserContract.SQL_CREATE_TABLE)
        Log.d("DatabaseHelper", "Database and tables created successfully.")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(UserContract.SQL_DROP_TABLE)
        Log.d("DatabaseHelper", "Dropped existing tables.")
        onCreate(db)
    }

    fun logTableSchema(db: SQLiteDatabase, tableName: String) {
        val cursor: Cursor = db.rawQuery("PRAGMA table_info($tableName)", null)
        if (cursor.moveToFirst()) {
            do {
                val columnName = cursor.getString(cursor.getColumnIndex("name"))
                val columnType = cursor.getString(cursor.getColumnIndex("type"))
                val isNullable = cursor.getInt(cursor.getColumnIndex("notnull")) == 0
                Log.d("DatabaseHelper", "Table: $tableName, Column: $columnName, Type: $columnType, Nullable: $isNullable")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
    companion object {
        const val DATABASE_NAME = "my_database.db"
        const val DATABASE_VERSION = 1
    }
}
