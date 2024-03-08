package db
object UserContract {
        const val TABLE_NAME = "users"
        object Columns {
            const val COLUMN_ID = "id"
            const val COLUMN_EMAIL = "email"
            const val COLUMN_PASSWORD = "password"
            // Add more columns as needed
        }
        const val SQL_CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                ${Columns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Columns.COLUMN_EMAIL} TEXT NOT NULL UNIQUE,
                ${Columns.COLUMN_PASSWORD} TEXT NOT NULL,
                // Define column constraints as needed
            )
        """
        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
