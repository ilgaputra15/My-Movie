package com.gyosanila.mymovie.features.fragmentMovie

import com.gyosanila.mymovie.features.domain.network.BaseResponse
import com.gyosanila.mymovie.features.domain.network.MovieItem
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
                this::onFailureGetMovie
            )
    }

    override fun searchMovie(query: String) {
        subscriber = movieRepository.searchMovie(query)
            .doOnSubscribe{ view.setProgressBar(true) }
            .doAfterTerminate{
                view.setProgressBar(false)
            }
            .subscribe(
                this::onSuccessSearchMovie,
                this::onFailureGetMovie
            )

    }

    override fun onDestroy() {
        subscriber?.dispose()
    }

    private fun onSuccessGetMovieList(movieList: BaseResponse<MovieItem>) {
        view.setMovieList(movieList.results)
    }

    private fun onSuccessSearchMovie(movieList: BaseResponse<MovieItem>) {
        view.setSearchMovie(movieList.results)
    }

    private fun onFailureGetMovie(error: Throwable) {
        view.showError(error)
    }

}