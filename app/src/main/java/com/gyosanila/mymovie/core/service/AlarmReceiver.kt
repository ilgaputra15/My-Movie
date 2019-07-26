package com.gyosanila.mymovie.core.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.AlarmManager
import android.app.PendingIntent
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.utils.NotificationUtils
import com.gyosanila.mymovie.features.domain.network.MovieItem
import com.gyosanila.mymovie.features.domain.repository.MovieRepository
import com.gyosanila.mymovie.features.splash.SplashActivity
import io.reactivex.disposables.Disposable


/**
 * Created by ilgaputra15
 * on Friday, 26/07/2019 14:02
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val REQUEST_DAILY = 101
        const val REQUEST_RELEASE = 102
        private const val EXTRA_TYPE = "type"
        private const val EXTRA_MESSAGE = "message"
        private const val EXTRA_TITLE = "title"
        private const val TIME_FORMAT = "HH:mm"

    }

    private var subscriber: Disposable? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getIntExtra(EXTRA_TYPE, 0)
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        val title = intent?.getStringExtra(EXTRA_TITLE)

        when (type) {
            REQUEST_RELEASE -> getDiscoveryMovie(context!!)
            REQUEST_DAILY -> {
                NotificationUtils.showNotification(
                    context!!,
                    title!!,
                    message!!,
                    REQUEST_DAILY,
                    intent
                )
            }
        }
    }

    fun setRepeatingAlarm(context: Context, requestCode: Int, time: String, title: String = "", message: String = "") {
        if (isDateInvalid(time, TIME_FORMAT)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, requestCode)
        intent.putExtra(EXTRA_TITLE, title)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show()
        subscriber?.dispose()
    }

    private fun getDiscoveryMovie(context: Context) {
        val calender = Calendar.getInstance()
        val releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calender.time)
        val movieRepo = MovieRepository()
        subscriber = movieRepo.getDiscovery(releaseDate)
            .subscribe {
                sendNotification(it.results, context)
            }

    }

    private fun sendNotification(listMovie: List<MovieItem>, context: Context) {
        val intent = Intent(context, SplashActivity::class.java)
        for (movie in listMovie) {
            val message = context.getString(R.string.text_release_remainder, movie.original_title)
            NotificationUtils.showNotification(
                context,
                movie.title,
                message,
                REQUEST_RELEASE,
                intent,
                true
            )
        }
    }

    private fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }

    }

}