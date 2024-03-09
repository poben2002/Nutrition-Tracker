package edu.uw.ischool.haeun.nutritiontracker

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class UserSettingFragment : PreferenceFragmentCompat() {
    private val SETTINGS_WEIGHT = "settings_weight"
    private val SETTINGS_HEIGHT = "settings_height"

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_settings, rootKey)
        val weightPreference: EditTextPreference? = findPreference(SETTINGS_WEIGHT)
        val heightPreference: EditTextPreference? = findPreference(SETTINGS_HEIGHT)

        weightPreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        heightPreference?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }
}