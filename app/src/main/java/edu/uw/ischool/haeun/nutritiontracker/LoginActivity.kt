package edu.uw.ischool.haeun.nutritiontracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import db.UserDao
import db.DatabaseHelper
import edu.uw.ischool.haeun.nutritiontracker.HomepageActivity
import edu.uw.ischool.haeun.nutritiontracker.SignupActivity
import edu.uw.ischool.haeun.nutritiontracker.R

class LoginActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        userDao = UserDao(this)
        databaseHelper = DatabaseHelper(this)

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val signUpTextView: TextView = findViewById(R.id.signUpTextView)

        // Check if the app has storage permission, request if not
        if (!hasStoragePermission()) {
            requestStoragePermission()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate the form
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = userDao.getUserByEmail(email)
                if (user != null && user.password == password) {
                    startActivity(Intent(this, HomepageActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter an email AND a password", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for sign up text view
        signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java)) // Navigate to SignupActivity
        }
    }

    private fun hasStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, handle accordingly
            } else {
                // Permission denied, handle accordingly or inform the user
                Toast.makeText(
                    this,
                    "Storage permission required to access files",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val STORAGE_PERMISSION_REQUEST_CODE = 100
    }
}
