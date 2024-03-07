package edu.uw.ischool.haeun.nutritiontracker

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    val SETTINGS_NOTIFICATION_HOUR = "settings_notification_hour"
    val SETTINGS_NOTIFICATION_MINUTE = "settings_notification_minute"
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker.
        val sharedPreferences = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val c = Calendar.getInstance()
        val currHour = c.get(Calendar.HOUR_OF_DAY)
        val currMinute = c.get(Calendar.MINUTE)
        val hour = sharedPreferences?.getInt(SETTINGS_NOTIFICATION_HOUR, currHour)
        val minute = sharedPreferences?.getInt(SETTINGS_NOTIFICATION_MINUTE, currMinute)

        // Create a new instance of TimePickerDialog and return it.
        return TimePickerDialog(
            context,
            this,
            hour ?: currHour,
            minute ?: currMinute,
            DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        context?.let {
            PreferenceManager.getDefaultSharedPreferences(it).edit().apply {
                putInt("settings_notification_hour", hourOfDay)
                putInt("settings_notification_minute", minute)
                apply()
            }
        }
    }
}
