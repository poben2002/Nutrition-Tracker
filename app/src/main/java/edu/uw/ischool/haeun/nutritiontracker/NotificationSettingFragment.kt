package edu.uw.ischool.haeun.nutritiontracker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class NotificationSettingFragment : PreferenceFragmentCompat() {
    private val SETTINGS_NOTIFICATION_TIME = "settings_notification_time"
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.notification_settings, rootKey)

        val notificationPreference: Preference? = findPreference(SETTINGS_NOTIFICATION_TIME)
        notificationPreference?.setOnPreferenceClickListener {
            TimePickerFragment().show(parentFragmentManager, "timePicker")
            true
        }
    }

}