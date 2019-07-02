package com.gyosanila.mymovie.features.fragmentMovie

import com.gyosanila.mymovie.features.domain.network.MovieList
import com.gyosanila.mymovie.features.domain.repository.MovieRepository
import io.reactivex.disposables.Disposable

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 10:06
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentMoviePresenter(
    private val view: FragmentMovie,
    private val movieRepository: MovieRepository = MovieRepository()
) : FragmentMovieContract.Presenter {

    private var subscriber: Disposable? = null

    override fun getListMovie() {
        subscriber = movieRepository.getMovieList()
            .doOnSubscribe{ view.setProgressBar(true) }
            .doAfterTerminate{
                view.setProgressBar(false)
            }
            .subscribe(
                this::onSuccessGetMovieList,
                this::onFailureGetMovieList
            )
    }

    override fun onDestroy() {
        subscriber?.dispose()
    }

    private fun onSuccessGetMovieList(movieList: MovieList) {
        view.setMovieList(movieList.results)
    }

    private fun onFailureGetMovieList(error: Throwable) {
        view.showError(error)
    }

}