package edu.uw.ischool.haeun.nutritiontracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import db.User
import db.UserDao
import db.DatabaseHelper

class SignupActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        userDao = UserDao(this)
        databaseHelper = DatabaseHelper(this)

        val userName = findViewById<EditText>(R.id.UserName)
        val emailEditText = findViewById<EditText>(R.id.emailSignUpEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordSignUpEditText)
        val passwordEditTextConfirm = findViewById<EditText>(R.id.passwordConfirmEditText)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val username = userName.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordConfirm = passwordEditTextConfirm.text.toString()


            if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                passwordConfirm.isEmpty()) {
                Log.e("SignupActivity", "All fields are required")
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                passwordEditTextConfirm.error = "Password is not matched"
                return@setOnClickListener
            }

            // Assuming the user input is valid, proceed with signup
            // Here, you should add the user to the database
            val user = User(0, email, password)
            userDao.insertUser(user)

            // Log out the contents of the database
            logDatabaseContents()

            // Start the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun logDatabaseContents() {
        // Retrieve all users from the database and log them
        val users = userDao.getAllUsers()
        for (user in users) {
            Log.d("Database", "User: $user")
        }
    }
}
