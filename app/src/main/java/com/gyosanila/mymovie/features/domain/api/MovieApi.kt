package com.gyosanila.mymovie.features.domain.api

import com.gyosanila.mymovie.features.domain.network.BaseResponse
import com.gyosanila.mymovie.features.domain.network.MovieDetail
import com.gyosanila.mymovie.features.domain.network.MovieItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ilgaputra15
 * on Monday, 01/07/2019 15:33
 * Division Mobile - PT.Homecareindo Global Medika
 **/
interface MovieService {
    @GET("3/movie/upcoming")
    fun getListMovie(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ) : Observable<BaseResponse<MovieItem>>

    @GET("3/search/movie")
    fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ) : Observable<BaseResponse<MovieItem>>

    @GET("3/movie/{idMovie}")
    fun getMovieDetail(
        @Path("idMovie") idMovie: Int,
        @Query("api_key") api_key: String
    ) : Observable<MovieDetail>
}