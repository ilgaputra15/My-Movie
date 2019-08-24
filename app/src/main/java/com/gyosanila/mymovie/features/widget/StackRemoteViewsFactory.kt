package com.gyosanila.mymovie.features.widget

import android.content.Context
import android.widget.RemoteViews
import android.content.Intent
import android.os.Bundle
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.RemoteViewsService
import com.gyosanila.mymovie.features.domain.local.MyMovieRepository
import com.gyosanila.mymovie.features.domain.local.MyMovieRoomDatabase
import android.util.Log
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.features.domain.network.MovieItem
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ilgaputra15
 * on Sunday, 14/07/2019 19:02
 * Division Mobile - PT.Homecareindo Global Medika
 **/

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private var movie = ArrayList<MovieItem>()
    private var listImage = ArrayList<Bitmap>()

    override fun onCreate() {}
    override fun onDestroy() {}
    override fun getCount(): Int = movie.size
    override fun getLoadingView(): RemoteViews? = null
    override fun getViewTypeCount(): Int = 1
    override fun getItemId(i: Int): Long = 0
    override fun hasStableIds(): Boolean = false

    override fun onDataSetChanged() {
        val wordsDao = MyMovieRoomDatabase.getDatabase(mContext).movieDao()
        val repository = MyMovieRepository(wordsDao)
        Log.d("database", "test database")
        movie = repository.getMovieFavorites() as ArrayList<MovieItem>
        for (movie in movie) {
            Log.d("database", movie.title)
            listImage.add(getBitmapFromURL(Constant.ImageUrl+movie.poster_path)!!)
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.widget_item)
        if (movie.isNotEmpty()) {
            remoteViews.setImageViewBitmap(R.id.imageWidget, listImage[position])
            val extras = Bundle()
            extras.putString(MyMovieWidget.EXTRA_ITEM, movie[position].title)
            val fillInIntent = Intent()
            fillInIntent.putExtras(extras)
            remoteViews.setOnClickFillInIntent(R.id.imageWidget, fillInIntent)
        }
        return remoteViews
    }


    private fun getBitmapFromURL(src: String): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            null
        }
    }

}