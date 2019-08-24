package com.gyosanila.mymoviefavorites.feature.home

import android.content.Context
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import java.lang.ref.WeakReference
import android.net.Uri
import android.database.ContentObserver
import android.os.Handler
import com.gyosanila.mymoviefavorites.R
import android.os.HandlerThread
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyosanila.mymoviefavorites.feature.adapter.MovieAdapter
import com.gyosanila.mymoviefavorites.feature.domain.network.LoadMovieCallback
import com.gyosanila.mymoviefavorites.feature.domain.network.MovieFavorites
import kotlinx.android.synthetic.main.activity_home.*
import androidx.appcompat.app.AlertDialog


class HomeActivity : AppCompatActivity(), LoadMovieCallback {

    companion object {
        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority("com.gyosanila.mymovie")
            .appendPath("movie_table")
            .build()
    }

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = DataObserver(handler, this)
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        GetData(this, this).execute()
        movieAdapter = MovieAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        swipeRefresh.setOnRefreshListener {
            GetData(this, this).execute()
        }

    }

    override fun postExecute(movie: Cursor?) {
        swipeRefresh.isRefreshing = false
        if (movie != null) {
            setMovieFavorites(movie)
        } else {
            dialog()
        }
    }

    private fun setMovieFavorites(movie: Cursor) {
        val movieFavorites = ArrayList<MovieFavorites>()
        movie.moveToFirst()
        while (!movie.isAfterLast) {
            movieFavorites.add(
                MovieFavorites(
                    movie.getString(0),
                    movie.getString(1),
                    movie.getString(2),
                    movie.getString(3)
                )
            )
            movie.moveToNext()
        }
        movieAdapter.setListMovie(movieFavorites)
    }

    private class GetData(context: Context, callback: LoadMovieCallback) :
        AsyncTask<Void, Void, Cursor?>() {
        private val weakContext: WeakReference<Context> = WeakReference(context)
        private val weakCallback: WeakReference<LoadMovieCallback>? = WeakReference(callback)

        override fun doInBackground(vararg voids: Void): Cursor? {
            return weakContext.get()?.contentResolver?.query(
                CONTENT_URI,
                null,
                null,
                null,
                null)

        }
        override fun onPostExecute(data: Cursor?) {
            super.onPostExecute(data)
            weakCallback?.get()?.postExecute(data)
        }
    }

    fun dialog() {
        val dialogMessage = "Tolong install aplikasi my movie terlebih dahulu untuk menggunakan aplikasi ini!!!"
        val dialogTitle = "Warning"
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                finish()
            }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    internal class DataObserver(handler: Handler, private val context: Context) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            GetData(context, context as HomeActivity).execute()
        }
    }
}