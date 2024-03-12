package edu.uw.ischool.haeun.nutritiontracker

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        // Retrieve the notification time from SharedPreferences
        Log.d("Notification", "Received notification broadcast")
        val preferences = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val remainingCalories = preferences?.getInt("remaining_calories", 0)

        // Create a notification
        val notification = NotificationCompat.Builder(context!!, "NutritionTracker")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Nutrition Tracker")
            .setContentText("Remaining Calories: $remainingCalories")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setChannelId("NutritionTracker")
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notification)
    }
}