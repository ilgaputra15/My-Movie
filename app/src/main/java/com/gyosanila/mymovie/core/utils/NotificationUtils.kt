package com.gyosanila.mymovie.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.gyosanila.mymovie.R
import java.util.*

/**
 * Created by ilgaputra15
 * on Friday, 26/07/2019 17:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

object NotificationUtils {

    class NotificationItem(val id: Int, val title: String, val message: String)
    private val stackNotification = mutableListOf<NotificationItem>()
    private var idNotification = 0
    private const val GROUP_MOVIE = "groupMovie"
    private const val MAX_NOTIFICATIONS = 5

    fun showNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int,
        pendingIntent: Intent?,
        isStackView: Boolean = false
    ) {
        val channelId = context.packageName
        val channelName = context.packageCodePath
        val randomId = (Date().time/1000).toInt()
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        pendingIntent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val contentIntent = PendingIntent
            .getActivity(context, randomId, pendingIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = notificationBuilder(
            context,
            channelId,
            title,
            message,
            alarmSound,
            contentIntent
        )

        if (isStackView) {
            stackNotification.add(NotificationItem(idNotification, title, message))
            builder.setGroup(GROUP_MOVIE)
            if (idNotification > MAX_NOTIFICATIONS) {
                val inboxStyle = NotificationCompat.InboxStyle()
                    .addLine(stackNotification[idNotification - 2].message)
                    .addLine(stackNotification[idNotification - 1].message)
                    .addLine(stackNotification[idNotification].message)
                    .setBigContentTitle(title)
                builder.setGroupSummary(true)
                    .setStyle(inboxStyle)
            }
            idNotification++
        }
        setChannel(builder, channelId, channelName, notificationManagerCompat, alarmSound, message)
        notificationManagerCompat.notify(notificationId, builder.build())
    }

    private fun notificationBuilder(
        context: Context,
        channelId: String,
        title: String,
        message: String,
        alarmSound: Uri?,
        contentIntent: PendingIntent?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.black))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
    }


    private fun setChannel(
        builder: NotificationCompat.Builder,
        channelId: String,
        channelName: String,
        notificationManager: NotificationManager,
        alarmSound: Uri?,
        message: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channelId)
            notificationManager.createNotificationChannel(channel)

            channel.lightColor = Color.GRAY
            channel.enableLights(true)
            channel.description = message
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel.setSound(alarmSound, audioAttributes)
        }
    }
}