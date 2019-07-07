package com.gyosanila.mymovie.features.tvShowDetail

import com.gyosanila.mymovie.features.domain.network.TvShowDetail
import com.gyosanila.mymovie.features.domain.network.TvShowItem

/**
 * Created by ilgaputra15
 * on Wednesday, 03/07/2019 10:11
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class TvShowDetailContract {
    interface Presenter {
        fun getTvShowDetail(idTvShow: Int)
        fun onDestroy()
    }

    interface View {
        fun getDataIntent()
        fun setupUI()
        fun getTvShowDetail()
        fun setFavorite(listTvShow: List<TvShowItem>)
        fun setIconFavorite(isFavorite: Boolean)
        fun toastAddFavorites(isAdd: Boolean)
        fun showTvShowDetail(tvShowDetail: TvShowDetail)
        fun setProgressBar(isShow: Boolean)
        fun showError(error: Throwable)
    }
}