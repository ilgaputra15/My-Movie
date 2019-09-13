package com.gyosanila.mymovie.features.tvShowDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gyosanila.mymovie.core.common.ErrorState
import com.gyosanila.mymovie.core.service.RetrofitService
import com.gyosanila.mymovie.features.domain.local.MyMovieRepository
import com.gyosanila.mymovie.features.domain.local.MyMovieRoomDatabase
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import com.gyosanila.mymovie.features.domain.repository.TvShowRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.gyosanila.mymovie.features.domain.network.ResultResponse



/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 19:25
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class TvShowViewModel(
    application: Application
): AndroidViewModel(application), TvShowDetailContract.ViewModel {

    val repository: MyMovieRepository
    val allTvShow: LiveData<List<TvShowItem>>
    private val tvShowRepo: TvShowRepository
    private var tvShowResponse: MutableLiveData<ResultResponse>? = null

    init {
        val wordsDao = MyMovieRoomDatabase.getDatabase(application).movieDao()
        repository = MyMovieRepository(wordsDao)
        allTvShow = repository.allTvShow
        tvShowRepo = TvShowRepository(RetrofitService.movieAPI())
    }


    override fun detailTvShow(idTvShow: Int): MutableLiveData<ResultResponse>? {
        if (tvShowResponse == null) {
            tvShowResponse = MutableLiveData()
            fetchDetailTvShow(idTvShow)
        }
        return tvShowResponse
    }

    override fun fetchDetailTvShow(idTvShow: Int) {
        tvShowResponse?.postValue(ResultResponse.OnLoading(true))
        viewModelScope.launch {
            try {
                val user =  tvShowRepo.getTvShowDetail(idTvShow)
                if (user.isSuccessful) {
                    tvShowResponse?.postValue(ResultResponse.Success(user.body()))
                } else {
                    tvShowResponse?.postValue(ResultResponse.Error(ErrorState.RESPONSE, user.code()))
                }
            }catch (e: HttpException) {
                tvShowResponse?.postValue(ResultResponse.Error(ErrorState.NETWORK))
            } catch (e: Throwable) {
                tvShowResponse?.postValue(ResultResponse.Error(ErrorState.NETWORK))
            }
        }
    }

    override fun insertTvShow(tvShow: TvShowItem) {
        viewModelScope.launch {
            repository.insertTvShow(tvShow)
        }
    }

    override fun deleteTvShowById(idTvShow: Int) {
        viewModelScope.launch {
            repository.deleteTvShow(idTvShow)
        }
    }
}