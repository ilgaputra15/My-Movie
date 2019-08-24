package com.gyosanila.mymovie.features.domain.repository

import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.core.service.RetrofitService
import com.gyosanila.mymovie.core.utils.RxUtils
import com.gyosanila.mymovie.features.domain.api.TvShowApi
import com.gyosanila.mymovie.features.domain.network.BaseResponse
import com.gyosanila.mymovie.features.domain.network.TvShowDetail
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import io.reactivex.Observable

/**
 * Created by ilgaputra15
 * on Monday, 01/07/2019 17:19
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class TvShowRepository(private val tvShowApi: TvShowApi = RetrofitService.movieAPI()) {

    fun getTvShowList() : Observable<BaseResponse<TvShowItem>> {
        return tvShowApi.getListTvShow(Constant.MovieAPIKey, 1).compose(RxUtils.applyObservableAsync())
    }

    fun searchTvShow(query: String) : Observable<BaseResponse<TvShowItem>> {
        return tvShowApi.searchTvShow(Constant.MovieAPIKey, query).compose(RxUtils.applyObservableAsync())
    }

    fun getTvShowDetail(id: Int) : Observable<TvShowDetail> {
        return tvShowApi.getTvShowDetail(id, Constant.MovieAPIKey).compose(RxUtils.applyObservableAsync())
    }
}