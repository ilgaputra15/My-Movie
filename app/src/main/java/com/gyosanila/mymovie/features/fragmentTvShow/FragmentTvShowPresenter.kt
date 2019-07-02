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
                this::onFailureGetTvShowList
            )
    }

    private fun onSuccessGetTvShowList(tvShowList: BaseResponse<TvShowItem>) {
        view.setTvShowList(tvShowList.results)
    }

    private fun onFailureGetTvShowList(error: Throwable) {
        view.showError(error)
    }

    override fun onDestroy() {
        subscriber?.dispose()
    }
}