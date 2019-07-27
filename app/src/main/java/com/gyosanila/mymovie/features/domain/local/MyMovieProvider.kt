package com.gyosanila.mymovie.features.domain.local

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

/**
 * Created by ilgaputra15
 * on Saturday, 27/07/2019 00:23
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class MyMovieProvider : ContentProvider() {

    private lateinit var database: MyMovieDao
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val authority = "com.gyosanila.mymovie"
    private val movie = 15


    override fun onCreate(): Boolean {
        database = MyMovieRoomDatabase.getDatabase(context!!).movieDao()
        sUriMatcher.addURI(authority, "movie_table", movie)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return if (sUriMatcher.match(uri) == movie) {
            val repository = MyMovieRepository(database)
            val listFavorites = repository.getMovieFavorites()
            val columns = arrayOf("_id", "title", "overview", "poster_path")
            val matrixCursor = MatrixCursor(columns)
            for (favorite in listFavorites) {
                matrixCursor.addRow(arrayOf(favorite.id, favorite.title, favorite.overview, favorite.poster_path))
            }
            matrixCursor
        } else null


    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }


    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}