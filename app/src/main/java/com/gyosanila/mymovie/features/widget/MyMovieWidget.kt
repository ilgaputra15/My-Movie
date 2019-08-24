package com.gyosanila.mymovie.features.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.gyosanila.mymovie.R
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.widget.Toast

/**
 * Created by ilgaputra15
 * on Sunday, 14/07/2019 19:02
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class MyMovieWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "MyMovie.TOAST_ACTION"
        const val EXTRA_ITEM = "MyMovie.EXTRA_ITEM"
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val intent = Intent(context, StackWidgetService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

        val views = RemoteViews(context.packageName, R.layout.my_movie_widget)
        views.setRemoteAdapter(R.id.stack_view, intent)
        views.setEmptyView(R.id.stack_view, R.id.empty_view)

        val toastIntent = Intent(context, MyMovieWidget::class.java)
        toastIntent.action = TOAST_ACTION
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

        val toastPendingIntent =
            PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null) {
            if (intent.action == TOAST_ACTION) {
                val viewTitle = intent.getStringExtra(EXTRA_ITEM)
                Toast.makeText(context, viewTitle, Toast.LENGTH_SHORT).show()
            }
        }
    }


}

