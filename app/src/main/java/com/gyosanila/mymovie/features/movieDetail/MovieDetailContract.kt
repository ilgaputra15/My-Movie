package com.gyosanila.mymovie.features.movieDetail

import com.gyosanila.mymovie.features.domain.network.MovieDetail

/**
 * Created by ilgaputra15
 * on Wednesday, 03/07/2019 10:11
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class MovieDetailContract {
    interface Presenter {
        fun getMovieDetail(idMovie: Int)
        fun onDestroy()
    }

    interface View {
        fun getMovieDetail()
        fun showMovieDetail(movieDetail: MovieDetail)
        fun setProgressBar(isShow: Boolean)
        fun showError(error: Throwable)
    }
}