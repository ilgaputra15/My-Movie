package com.gyosanila.mymovie.features.domain.repository

import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.core.service.RetrofitService
import com.gyosanila.mymovie.core.utils.RxUtils
import com.gyosanila.mymovie.features.domain.api.MovieService
import com.gyosanila.mymovie.features.domain.network.BaseResponse
import com.gyosanila.mymovie.features.domain.network.MovieItem
import io.reactivex.Observable

/**
 * Created by ilgaputra15
 * on Monday, 01/07/2019 17:19
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class MovieRepository(private val movieApi: MovieService = RetrofitService.movieAPI()) {

    fun getMovieList() : Observable<BaseResponse<MovieItem>> {
        return movieApi.getListMovie(Constant.MovieAPIKey, 1).compose(RxUtils.applyObservableAsync())
    }
}