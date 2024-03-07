package edu.uw.ischool.haeun.nutritiontracker

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import java.util.Calendar

class NotificationSettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.notification_settings, rootKey)

        val notificationPreference: Preference? = findPreference("settings_notification_time")
        notificationPreference?.setOnPreferenceClickListener {
            TimePickerFragment().show(parentFragmentManager, "timePicker")
            true
        }
    }
}