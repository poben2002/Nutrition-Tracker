package db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.lang.Exception

class UserDao(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insertUser(user: User): Long {
        val db = dbHelper.writableDatabase
        dbHelper.logTableSchema(db, UserContract.TABLE_NAME)
        val values = ContentValues().apply {
            put(UserContract.Columns.COLUMN_EMAIL, user.email)
            put(UserContract.Columns.COLUMN_PASSWORD, user.password)
        }
        return db.insert(UserContract.TABLE_NAME, null, values)
    }

    fun getUserByEmail(email: String): User? {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            UserContract.Columns.COLUMN_ID,
            UserContract.Columns.COLUMN_EMAIL,
            UserContract.Columns.COLUMN_PASSWORD
        )
        val selection = "${UserContract.Columns.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)
        val cursor: Cursor = db.query(
            UserContract.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var user: User? = null
        try {
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndex(UserContract.Columns.COLUMN_ID)
                val emailIndex = cursor.getColumnIndex(UserContract.Columns.COLUMN_EMAIL)
                val passwordIndex = cursor.getColumnIndex(UserContract.Columns.COLUMN_PASSWORD)
                // Check if all columns are present
                if (idIndex != -1 && emailIndex != -1 && passwordIndex != -1) {
                    val id = cursor.getLong(idIndex)
                    val userEmail = cursor.getString(emailIndex)
                    val password = cursor.getString(passwordIndex)

                    // Create User object
                    user = User(id, userEmail, password)
                } else {
                    // Handle missing columns
                    // Log.e("getUserByEmail", "Some columns are missing in the cursor")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
        }
        return user
    }

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            UserContract.Columns.COLUMN_ID,
            UserContract.Columns.COLUMN_EMAIL,
            UserContract.Columns.COLUMN_PASSWORD
        )
        val cursor: Cursor = db.query(
            UserContract.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(UserContract.Columns.COLUMN_ID))
                val userEmail = cursor.getString(cursor.getColumnIndex(UserContract.Columns.COLUMN_EMAIL))
                val password = cursor.getString(cursor.getColumnIndex(UserContract.Columns.COLUMN_PASSWORD))

                // Create User object and add to the list
                val user = User(id, userEmail, password)
                users.add(user)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
        }
        return users
    }
}
