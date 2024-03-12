package edu.uw.ischool.haeun.nutritiontracker

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager

class NotificationSettingActivity : AppCompatActivity() {
    private val SETTINGS_NOTIFICATION_HOUR = "settings_notification_hour"
    private val SETTINGS_NOTIFICATION_MINUTE = "settings_notification_minute"
    private val PERM_POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_setting)

        // Debug code to print all preferences
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.all.forEach {
            Log.d("Preferences", "${it.key} -> ${it.value}")
        }


        if (this.checkSelfPermission(PERM_POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED
            && this.shouldShowRequestPermissionRationale(PERM_POST_NOTIFICATIONS)
        ) {
            this.requestPermissions(
                arrayOf(PERM_POST_NOTIFICATIONS),
                0
            )
        }

        // Create the NotificationChannel.
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(name, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        //registerReceiver(Notification(), IntentFilter("edu.uw.ischool.haeun.nutritiontracker.NOTIFICATION"), RECEIVER_EXPORTED)
        preferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == SETTINGS_NOTIFICATION_HOUR || key == SETTINGS_NOTIFICATION_MINUTE) {
                Log.d("NotificationSettingFragment", "Notification time changed")
                val intent = Intent(this, Notification::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
                val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val time = getNotificationTime()
                Log.d("NotificationSettingFragment", "Setting alarm for $time")
                alarmManager.cancel(pendingIntent)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent)
                // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, pendingIntent)
            }
        }

        setSupportActionBar(findViewById(R.id.notificationSettingToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.notificationSettingFragment, NotificationSettingFragment())
            .commit()
    }
    private fun getNotificationTime(): Long {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val hour = sharedPreferences.getInt(SETTINGS_NOTIFICATION_HOUR, 0)
        val minute = sharedPreferences.getInt(SETTINGS_NOTIFICATION_MINUTE, 0)
        val c = java.util.Calendar.getInstance()
        c.set(java.util.Calendar.HOUR_OF_DAY, hour)
        c.set(java.util.Calendar.MINUTE, minute)
        c.set(java.util.Calendar.SECOND, 0)
        return c.timeInMillis
    }
}