package com.gyosanila.mymovie.features.tvShowDetail

import com.gyosanila.mymovie.features.domain.network.TvShowDetail
import com.gyosanila.mymovie.features.domain.repository.TvShowRepository
import io.reactivex.disposables.Disposable

/**
 * Created by ilgaputra15
 * on Wednesday, 03/07/2019 10:15
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class TvShowDetailPresenter(
    private val view: TvShowDetailActivity,
    private val tvShowRepo: TvShowRepository = TvShowRepository()
) : TvShowDetailContract.Presenter {

    private var subscriber: Disposable? = null

    override fun getTvShowDetail(idTvShow: Int) {
        subscriber = tvShowRepo.getTvShowDetail(idTvShow)
            .doOnSubscribe{ view.setProgressBar(true) }
            .doAfterTerminate{
                view.setProgressBar(false)
            }
            .subscribe(
                this::onSuccessGetTvShowDetail,
                this::onFailureGetTvShowDetail
            )
    }

    private fun onSuccessGetTvShowDetail(tvShowDetail: TvShowDetail) {
        view.showTvShowDetail(tvShowDetail)
    }

    private fun onFailureGetTvShowDetail(error: Throwable) {
        view.showError(error)
    }

    override fun onDestroy() {
        subscriber?.dispose()
    }
}