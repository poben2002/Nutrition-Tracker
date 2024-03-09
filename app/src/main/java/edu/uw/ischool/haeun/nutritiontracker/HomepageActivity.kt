package edu.uw.ischool.haeun.nutritiontracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import android.content.Intent


class HomepageActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.registerOnSharedPreferenceChangeListener(this)

        updateCalorieGoal(preferences)

        val nutritionDashBoardButton: Button = findViewById(R.id.nutritionDashboardButton)
        val notificationButton: Button = findViewById(R.id.notificationButton)
        val searchBarButton: Button = findViewById(R.id.searchBarButton)
        val userSettingsButton: Button = findViewById(R.id.userSettingsButton)

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

        val calorieGoal = CalorieCalculator.calculateCalorieGoals(
            height.toDouble(),
            weight.toDouble(),
            sex,
            age,
            exerciseDaysPerWeek,
            weightGoal
        )

        val calorieGoalTextView: TextView = findViewById(R.id.calorieGoalTextView)
        calorieGoalTextView.text = "Your daily calorie goal: $calorieGoal"
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }
}
