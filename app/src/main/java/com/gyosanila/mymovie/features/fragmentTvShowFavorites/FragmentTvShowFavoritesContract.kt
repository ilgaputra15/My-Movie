package com.gyosanila.mymovie.features.fragmentTvShowFavorites

import com.gyosanila.mymovie.features.domain.network.TvShowItem

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 10:07
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentTvShowFavoritesContract {
    interface View {
        fun setListTvShow(listTvShow: List<TvShowItem>)
    }
}