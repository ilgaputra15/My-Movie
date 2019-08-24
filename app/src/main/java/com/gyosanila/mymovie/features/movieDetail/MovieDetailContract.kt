package com.gyosanila.mymovie.features.movieDetail

import com.gyosanila.mymovie.features.domain.network.MovieDetail
import com.gyosanila.mymovie.features.domain.network.MovieItem

/**
 * Created by ilgaputra15
 * on Wednesday, 03/07/2019 10:11
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class MovieDetailContract {
    interface Presenter {
        fun getMovieDetail(idMovie: Int)
        fun onDestroy()
        fun setFavorite(movie: MovieItem)
    }

    interface View {
        fun getDataIntent()
        fun setupUI()
        fun updateWidget()
        fun getMovieDetail()
        fun setFavorite(listMovie: List<MovieItem>)
        fun showMovieDetail(movieDetail: MovieDetail)
        fun setProgressBar(isShow: Boolean)
        fun setIconFavorite(isFavorite: Boolean)
        fun showError(error: Throwable)
        fun toastAddFavorites(isAdd: Boolean)
    }
}