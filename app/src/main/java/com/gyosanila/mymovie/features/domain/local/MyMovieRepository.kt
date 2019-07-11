package com.gyosanila.mymovie.features.domain.local

import androidx.lifecycle.LiveData
import com.gyosanila.mymovie.features.domain.network.MovieItem
import com.gyosanila.mymovie.features.domain.network.TvShowItem


/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 10:37
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class MyMovieRepository(private val myMovieDao: MyMovieDao) {

    val allMovies: LiveData<List<MovieItem>> = myMovieDao.getAllMovies()
    val allTvShow: LiveData<List<TvShowItem>> = myMovieDao.getAllTvShow()

    suspend fun insertMovie(movie: MovieItem) {
        myMovieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(idMovie: Int) {
        myMovieDao.deleteMovieById(idMovie)
    }

    suspend fun insertTvShow(tvShow: TvShowItem) {
        myMovieDao.insertTvShow(tvShow)
    }

    suspend fun deleteTvShow(idTvShow: Int) {
        myMovieDao.deleteTvShowById(idTvShow)
    }
}