package com.gyosanila.mymovie.features.widget

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ComponentName
import android.appwidget.AppWidgetManager
import android.util.Log
import com.gyosanila.mymovie.R


/**
 * Created by ilgaputra15
 * on Thursday, 18/07/2019 13:11
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class UpdateWidgetService : JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("widget", "Start")
        val manager = AppWidgetManager.getInstance(this)
        val theWidget = ComponentName(this, MyMovieWidget::class.java)
        val id = manager.getAppWidgetIds(theWidget)
        manager.notifyAppWidgetViewDataChanged(id, R.id.stack_view)
        jobFinished(params, false)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("widget", "Stop")
        return false
    }
}