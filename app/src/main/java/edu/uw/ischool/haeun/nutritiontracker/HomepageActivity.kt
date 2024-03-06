package edu.uw.ischool.haeun.nutritiontracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val nutritionDashBoardButton: Button = findViewById(R.id.nutritionDashboardButton);
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
}