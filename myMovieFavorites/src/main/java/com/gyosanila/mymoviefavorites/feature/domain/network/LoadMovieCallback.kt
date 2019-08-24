package com.gyosanila.mymoviefavorites.feature.domain.network

import android.database.Cursor

/**
 * Created by ilgaputra15
 * on Saturday, 27/07/2019 09:54
 * Division Mobile - PT.Homecareindo Global Medika
 **/

internal interface LoadMovieCallback {
    fun postExecute(movie: Cursor?)
}