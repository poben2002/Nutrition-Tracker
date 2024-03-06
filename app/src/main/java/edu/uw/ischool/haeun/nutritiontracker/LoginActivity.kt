package edu.uw.ischool.haeun.nutritiontracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val signUpTextView: TextView = findViewById(R.id.signUpTextView)

        loginButton.setOnClickListener {
            if (validateForm(emailEditText, passwordEditText)) {
                // Assuming your HomepageActivity is the main activity after login
                startActivity(Intent(this, HomepageActivity::class.java))
            }
        }

        // Direct approach for navigation to SignUp
        signUpTextView.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun validateForm(emailEditText: EditText, passwordEditText: EditText): Boolean {
        var isValid = true

        val email = emailEditText.text.toString().trim()
        if (email.isEmpty()) {
            emailEditText.error = "Enter your email"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Enter a valid email address"
            isValid = false
        }

        val password = passwordEditText.text.toString().trim()
        if (password.isEmpty()) {
            passwordEditText.error = "Enter your password"
            isValid = false
        }

        return isValid
    }
}
