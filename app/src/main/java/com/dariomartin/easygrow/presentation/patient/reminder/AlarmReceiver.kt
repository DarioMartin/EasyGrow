package com.dariomartin.easygrow.presentation.patient.reminder

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.presentation.login.AuthActivity

class AlarmReceiver : BroadcastReceiver() {


    companion object{
        const val ACTION_ALARM_RECEIVER = "com.dariomartin.easygrow.action_reminder"
    }

    override fun onReceive(context: Context, intent: Intent) {

        val intent = Intent(context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationBuilder = NotificationCompat.Builder(context, "easygrow")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManagerCompat = NotificationManagerCompat.from(context)

        notificationManagerCompat.notify(123, notificationBuilder.build())
    }
}