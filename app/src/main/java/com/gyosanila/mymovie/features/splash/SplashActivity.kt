package com.gyosanila.mymovie.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.dashboard.DashboardActivity
import android.widget.Toast
import android.app.job.JobScheduler
import android.app.job.JobInfo
import com.gyosanila.mymovie.features.widget.UpdateWidgetService
import android.content.ComponentName
import android.content.Context
import com.gyosanila.mymovie.core.common.Alarm
import com.gyosanila.mymovie.core.service.AlarmReceiver
import com.gyosanila.mymovie.features.domain.local.SharedPreferences


class SplashActivity : AppCompatActivity() {

    private val jobId = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startJob()
        setAlarm()
        setFullScreen()
        counterToDashboard()
    }

    private fun setFullScreen() {
        window.setFlags(android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun counterToDashboard() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
               navigateToDashboard()
            }
        }.start()
    }

    private fun startJob() {
        val mServiceComponent = ComponentName(this, UpdateWidgetService::class.java)
        val builder = JobInfo.Builder(jobId, mServiceComponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        builder.setPeriodic(60000)
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(builder.build())
        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show()
    }

    fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun setAlarm() {
        val sharedPreferences = SharedPreferences(this)
        if (sharedPreferences.isFirstTime) {
            sharedPreferences.isFirstTime = false
            sharedPreferences.dailyRemainder = true
            sharedPreferences.releaseRemainder = true
            val alarm = AlarmReceiver()
            alarm.setRepeatingAlarm(
                this,
                AlarmReceiver.REQUEST_RELEASE,
                Alarm.TIME_RELEASE
            )
            alarm.setRepeatingAlarm(
                this,
                AlarmReceiver.REQUEST_DAILY,
                Alarm.TIME_DAILY,
                getString(R.string.app_name),
                getString(R.string.text_message_alarm_release)
            )
        }
    }
}
