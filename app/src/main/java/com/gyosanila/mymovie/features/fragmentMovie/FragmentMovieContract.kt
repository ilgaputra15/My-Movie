package com.gyosanila.mymovie.features.fragmentMovie

import com.gyosanila.mymovie.features.domain.network.MovieItem

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 10:07
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentMovieContract {
    interface Presenter {
        fun getListMovie()
        fun searchMovie(query: String)
        fun onDestroy()
    }

    interface View {
        fun getListMovie()
        fun searchMovie(query: String)
        fun setProgressBar(isShow: Boolean)
        fun setMovieList(movieList: List<MovieItem>)
        fun setSearchMovie(movieList: List<MovieItem>)
        fun showError(error: Throwable)
    }
}