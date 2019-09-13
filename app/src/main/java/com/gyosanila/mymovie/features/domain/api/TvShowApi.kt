package com.gyosanila.mymovie.features.domain.api

import com.gyosanila.mymovie.features.domain.network.BaseResponse
import com.gyosanila.mymovie.features.domain.network.TvShowDetail
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ilgaputra15
 * on Monday, 01/07/2019 15:33
 * Division Mobile - PT.Homecareindo Global Medika
 **/
interface TvShowApi {
    @GET("3/tv/popular")
    fun getListTvShow(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ) : Observable<BaseResponse<TvShowItem>>

    @GET("3/search/tv")
    fun searchTvShow(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ) : Observable<BaseResponse<TvShowItem>>

    @GET("3/tv/{id}")
    suspend fun getTvShowDetail(
        @Path("id") id: Int,
        @Query("api_key") api_key: String
    ) : Response<TvShowDetail>
}
