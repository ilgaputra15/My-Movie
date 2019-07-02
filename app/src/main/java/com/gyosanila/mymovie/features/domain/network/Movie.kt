package com.gyosanila.mymovie.features.domain.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by ilgaputra15
 * on Sunday, 23/06/2019 22:41
 * Division Mobile - PT.Homecareindo Global Medika
 **/

@Parcelize
data class MovieItem(
    @Json(name = "vote_count") val vote_count: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "video") val video: Boolean,
    @Json(name = "vote_average") val vote_average: Double,
    @Json(name = "title") val title: String,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "poster_path") val poster_path: String,
    @Json(name = "original_language") val original_language: String,
    @Json(name = "original_title") val original_title: String,
    @Json(name = "genre_ids") val genre_ids: List<Int>,
    @Json(name = "backdrop_path") val backdrop_path: String,
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "overview") val overview: String
) : Parcelable