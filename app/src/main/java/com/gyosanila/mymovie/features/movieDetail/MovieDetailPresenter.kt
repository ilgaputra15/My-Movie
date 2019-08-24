package com.gyosanila.mymovie.features.movieDetail

import com.gyosanila.mymovie.features.domain.network.MovieDetail
import com.gyosanila.mymovie.features.domain.network.MovieItem
import com.gyosanila.mymovie.features.domain.repository.MovieRepository
import io.reactivex.disposables.Disposable

/**
 * Created by ilgaputra15
 * on Wednesday, 03/07/2019 10:15
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class MovieDetailPresenter(
    private val view: MovieDetailActivity,
    private val movieRepo: MovieRepository = MovieRepository()
) : MovieDetailContract.Presenter {

    private var subscriber: Disposable? = null

    override fun getMovieDetail(idMovie: Int) {
        subscriber = movieRepo.getMovieDetail(idMovie = idMovie)
            .doOnSubscribe{ view.setProgressBar(true) }
            .doAfterTerminate{
                view.setProgressBar(false)
            }
            .subscribe(
                this::onSuccessGetTvShowList,
                this::onFailureGetTvShowList
            )
    }

    override fun setFavorite(movie: MovieItem) {
    }

    private fun onSuccessGetTvShowList(movieDetail: MovieDetail) {
        view.showMovieDetail(movieDetail)
    }

    private fun onFailureGetTvShowList(error: Throwable) {
        view.showError(error)
    }

    override fun onDestroy() {
        subscriber?.dispose()
    }
}