package com.gyosanila.mymovie.features.fragmentMovieFavorites

import com.gyosanila.mymovie.features.domain.network.MovieItem

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 10:07
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentMovieHistoryContract {
    interface View {
        fun setMovieList(movieList: List<MovieItem>)
    }
}