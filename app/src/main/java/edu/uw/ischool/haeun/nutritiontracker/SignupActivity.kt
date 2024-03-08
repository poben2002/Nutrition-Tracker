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
        val phoneEditText = findViewById<EditText>(R.id.phoneSignUpEditText)
        val heightEditText = findViewById<EditText>(R.id.heightEditText)
        val weightEditText = findViewById<EditText>(R.id.weightEditText)
        val calorieEditText = findViewById<EditText>(R.id.calorieEditText)
        val signUpButton = findViewById<Button>(R.id.signUpButton)

        signUpButton.setOnClickListener {
            val username = userName.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val passwordConfirm = passwordEditTextConfirm.text.toString()
            val phone = phoneEditText.text.toString()
            val height = heightEditText.text.toString()
            val weight = weightEditText.text.toString()
            val calorie = calorieEditText.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                passwordConfirm.isEmpty() || phone.isEmpty() || height.isEmpty() ||
                weight.isEmpty() || calorie.isEmpty()) {
                // Handle input validation errors
                // You can show an error message or highlight the fields with errors
                // For simplicity, I'm just logging an error message
                Log.e("SignupActivity", "All fields are required")
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
