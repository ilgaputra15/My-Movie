package com.gyosanila.mymovie.features.domain.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 11:19
 * Division Mobile - PT.Homecareindo Global Medika
 **/

data class BaseResponse<T>(
    @Json(name = "results") val results: List<T>,
    @Json(name = "page") val page: Int,
    @Json(name = "total_results") val total_results: Int,
    @Json(name = "dates") val dates: DateRange,
    @Json(name = "total_pages") val total_pages: Int
)

@Parcelize
data class DateRange(
    @Json(name = "maximum") val maximum: String,
    @Json(name = "minimum") val minimum: String
) : Parcelable