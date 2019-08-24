package com.gyosanila.mymovie.features.fragmentTvShow

import com.gyosanila.mymovie.features.domain.network.TvShowItem

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 11:26
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentTvShowContract {
    interface Presenter {
        fun getTvShowList()
        fun searchTvShow(query: String)
        fun onDestroy()
    }

    interface View {
        fun getTvShowList()
        fun searchTvShow(query: String)
        fun setProgressBar(isShow: Boolean)
        fun setTvShowList(tvShowList: List<TvShowItem>)
        fun setSearchTvShow(tvShowList: List<TvShowItem>)
        fun showError(error: Throwable)
    }
}