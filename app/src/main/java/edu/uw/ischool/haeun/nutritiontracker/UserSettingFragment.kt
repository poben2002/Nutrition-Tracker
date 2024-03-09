package edu.uw.ischool.haeun.nutritiontracker

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class UserSettingFragment : PreferenceFragmentCompat() {
    private val SETTINGS_AGE = "settings_age"
    // private val SETTINGS_SEX = "settings_sex"
    private val SETTINGS_WEIGHT = "settings_weight"
    private val SETTINGS_HEIGHT = "settings_height"
    // private val SETTINGS_WORKOUT_DAYS = "settings_workout_days"
    // private val SETTINGS_WEIGHT_GOAL = "settings_weight_goal"

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_settings, rootKey)
        val agePreference: EditTextPreference? = findPreference(SETTINGS_AGE)
        val weightPreference: EditTextPreference? = findPreference(SETTINGS_WEIGHT)
        val heightPreference: EditTextPreference? = findPreference(SETTINGS_HEIGHT)

        agePreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
        }
        weightPreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        heightPreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }
}