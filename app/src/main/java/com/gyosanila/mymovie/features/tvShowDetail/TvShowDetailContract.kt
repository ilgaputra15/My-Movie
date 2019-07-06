package com.gyosanila.mymovie.features.tvShowDetail

import com.gyosanila.mymovie.features.domain.network.TvShowDetail

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
        fun getTvShowDetail()
        fun showTvShowDetail(tvShowDetail: TvShowDetail)
        fun setProgressBar(isShow: Boolean)
        fun showError(error: Throwable)
    }
}