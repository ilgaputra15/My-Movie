package com.gyosanila.mymovie.features.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by ilgaputra15
 * on Sunday, 14/07/2019 18:59
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}
