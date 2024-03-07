package edu.uw.ischool.haeun.nutritiontracker

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

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