package com.gyosanila.mymovie.features.network

/**
 * Created by ilgaputra15
 * on Sunday, 23/06/2019 22:41
 * Division Mobile - PT.Homecareindo Global Medika
 **/

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val photoResource: Int,
    val publish_at: String
)