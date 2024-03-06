package edu.uw.ischool.haeun.nutritiontracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

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

            if (username.isEmpty()) {
                userName.error = "Enter your name"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                emailEditText.error = "Enter your email"
                return@setOnClickListener
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Enter a valid email address"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Enter your password"
                return@setOnClickListener
            }

            if (passwordConfirm.isEmpty() || password != passwordConfirm) {
                passwordEditTextConfirm.error = "Password confirmation does not match"
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                phoneEditText.error = "Enter your phone number"
                return@setOnClickListener
            } else if (phone.length != 10) {
                phoneEditText.error = "Enter a valid phone number"
                return@setOnClickListener
            }

            if (height.isEmpty()) {
                heightEditText.error = "Enter your height"
                return@setOnClickListener
            }

            if (weight.isEmpty()) {
                weightEditText.error = "Enter your weight"
                return@setOnClickListener
            }

            if (calorie.isEmpty()) {
                calorieEditText.error = "Enter your daily calorie goal"
                return@setOnClickListener
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
