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


class SplashActivity : AppCompatActivity() {

    private val jobId = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startJob()
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
}
