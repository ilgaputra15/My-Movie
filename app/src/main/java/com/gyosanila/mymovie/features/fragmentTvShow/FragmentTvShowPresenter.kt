package com.gyosanila.mymovie.features.fragmentTvShow

import com.gyosanila.mymovie.features.domain.network.BaseResponse
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import com.gyosanila.mymovie.features.domain.repository.TvShowRepository
import io.reactivex.disposables.Disposable

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 11:26
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentTvShowPresenter(
    private val view: FragmentTvShow,
    private val tvShowRepository: TvShowRepository = TvShowRepository()
) : FragmentTvShowContract.Presenter {

    private var subscriber: Disposable? = null

    override fun getTvShowList() {
        subscriber = tvShowRepository.getTvShowList()
            .doOnSubscribe{ view.setProgressBar(true) }
            .doAfterTerminate{
                view.setProgressBar(false)
            }
            .subscribe(
                this::onSuccessGetTvShowList,
                this::onFailureGetTvShow
            )
    }

    override fun searchTvShow(query: String) {
        subscriber = tvShowRepository.searchTvShow(query)
            .doOnSubscribe{ view.setProgressBar(true) }
            .doAfterTerminate{
                view.setProgressBar(false)
            }
            .subscribe(
                this::onSuccessSearchTvShow,
                this::onFailureGetTvShow
            )
    }

    private fun onSuccessGetTvShowList(tvShowList: BaseResponse<TvShowItem>) {
        view.setTvShowList(tvShowList.results)
    }

    private fun onSuccessSearchTvShow(tvShowList: BaseResponse<TvShowItem>) {
        view.setSearchTvShow(tvShowList.results)
    }

    private fun onFailureGetTvShow(error: Throwable) {
        view.showError(error)
    }

    override fun onDestroy() {
        subscriber?.dispose()
    }
}