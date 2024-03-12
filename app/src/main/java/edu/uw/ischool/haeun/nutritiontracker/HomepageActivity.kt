package edu.uw.ischool.haeun.nutritiontracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast

class HomepageActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var calorieGoal: Int = 0
    private var newCalsConsumed: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.registerOnSharedPreferenceChangeListener(this)

        // Retrieve data from AddDataActivity intent
        val foodName = intent.getStringExtra("foodName")
        val portionSize = intent.getFloatExtra("portionSize", 0.0f)
        val calorieCount = intent.getIntExtra("calorieCount", 0)

        // Update dashboard UI
        val dashboardTextView: TextView = findViewById(R.id.recentlyAddedFoodTextView)

        // Check if the data is not null
        if (foodName != null && portionSize > 0 && calorieCount > 0) {
            // If data is not null, set the text and make the TextView visible
            val currentText = dashboardTextView.text.toString()
            val newText = "$currentText\n$foodName - Portion: $portionSize, Calories: $calorieCount"
            dashboardTextView.text = newText
            dashboardTextView.visibility = View.VISIBLE
        } else {
            // If data is null, hide the TextView
            dashboardTextView.visibility = View.GONE
        }

        // Update total calories consumed
        newCalsConsumed = calorieCount

        updateCalorieGoal(preferences)
        updateRemainingCalories()

        val nutritionDashBoardButton: Button = findViewById(R.id.nutritionDashboardButton)
        val notificationButton: Button = findViewById(R.id.notificationButton)
        val searchBarButton: Button = findViewById(R.id.searchBarButton)
        val userSettingsButton: Button = findViewById(R.id.userSettingsButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        logoutButton.setOnClickListener {
            logout()
        }

        resetButton.setOnClickListener {
            resetCalories()
        }

        nutritionDashBoardButton.setOnClickListener {
            startActivity(Intent(this, AddDataActivity::class.java))
        }
        notificationButton.setOnClickListener {
            startActivity(Intent(this, NotificationSettingActivity::class.java))
        }
        searchBarButton.setOnClickListener {
            startActivity(Intent(this, SearchDataActivity::class.java))
        }
        userSettingsButton.setOnClickListener {
            startActivity(Intent(this, UserSettingActivity::class.java))
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key != null) {
            if (key == "settings_age" || key == "settings_weight" || key == "settings_height" ||
                key == "settings_workout_days" || key == "settings_weight_goal" || key == "settings_sex") {
                updateCalorieGoal(sharedPreferences)
            }
        }
    }

    private fun updateCalorieGoal(preferences: SharedPreferences?) {
        val ageString = preferences?.getString("settings_age", "0")
        val age = ageString?.toIntOrNull() ?: 0
        val weightString = preferences?.getString("settings_weight", "0")
        val weight = weightString?.toFloatOrNull() ?: 0f
        val heightString = preferences?.getString("settings_height", "0")
        val height = heightString?.toFloatOrNull() ?: 0f
        val exerciseDaysPerWeekString = preferences?.getString("settings_workout_days", "0")
        val exerciseDaysPerWeek = exerciseDaysPerWeekString?.toIntOrNull() ?: 0
        val weightGoal = preferences?.getString("settings_weight_goal", "") ?: "maintain"
        val sex = preferences?.getString("settings_sex", "female") ?: "female"

        calorieGoal = CalorieCalculator.calculateCalorieGoals(
            height.toDouble(),
            weight.toDouble(),
            sex,
            age,
            exerciseDaysPerWeek,
            weightGoal
        )

        val calorieGoalTextView: TextView = findViewById(R.id.calorieGoalTextView)
        calorieGoalTextView.text = "Your daily calorie goal: $calorieGoal"

        updateRemainingCalories()
    }

    private fun updateRemainingCalories() {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // save new calorie amount eaten
        editor.putInt("totalCaloriesConsumed", newCalsConsumed + sharedPreferences.getInt("totalCaloriesConsumed", 0))
        newCalsConsumed = 0
        editor.apply()

        val remainingCalories = calorieGoal - sharedPreferences.getInt("totalCaloriesConsumed", 0)
        val remainingCaloriesTextView: TextView = findViewById(R.id.remainingCaloriesTextView)
        val goalReached: TextView = findViewById(R.id.goalReachedTextView)
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("remaining_calories", remainingCalories).apply()
        remainingCaloriesTextView.text = "Remaining Calories: $remainingCalories"
        if (remainingCalories <= 0) {
            goalReached.visibility = View.VISIBLE
            Toast.makeText(this@HomepageActivity, "Congratulations! You Hit Today's Goal!", Toast.LENGTH_LONG).show()
        } else {
            goalReached.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("totalCaloriesConsumed", 0)
        newCalsConsumed = 0
        editor.apply()

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.edit().clear().apply()

        // Navigate to the login screen
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun resetCalories() {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("totalCaloriesConsumed", 0)
        newCalsConsumed = 0
        editor.apply()
        updateRemainingCalories()
    }

}
