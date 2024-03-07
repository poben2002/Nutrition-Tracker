package edu.uw.ischool.haeun.nutritiontracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager

class NotificationSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_setting)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this).all
        preferences.forEach {
            Log.d("Preferences", "${it.key} -> ${it.value}")
        }

        setSupportActionBar(findViewById(R.id.notificationSettingToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notificationSettingFragment, NotificationSettingFragment())
            .commit()
    }
}