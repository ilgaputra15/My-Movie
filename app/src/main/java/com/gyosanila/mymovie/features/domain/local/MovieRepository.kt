package com.gyosanila.mymovie.features.domain.local

import androidx.lifecycle.LiveData
import com.gyosanila.mymovie.features.domain.network.MovieItem


/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 10:37
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class MovieRepository(private val movieDao: MovieDao) {

    val allMovies: LiveData<List<MovieItem>> = movieDao.getAllMovies()

    suspend fun insertMovie(movie: MovieItem) {
        movieDao.insert(movie)
    }

    suspend fun deleteMovie(idMovie: Int) {
        movieDao.deleteMovieById(idMovie)
    }
}