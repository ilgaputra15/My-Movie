package com.gyosanila.mymovie.features.tvShowDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gyosanila.mymovie.features.domain.local.MyMovieRepository
import com.gyosanila.mymovie.features.domain.local.MyMovieRoomDatabase
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import kotlinx.coroutines.launch

/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 19:25
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class TvShowViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: MyMovieRepository
    val allTvShow: LiveData<List<TvShowItem>>

    init {
        val wordsDao = MyMovieRoomDatabase.getDatabase(application).movieDao()
        repository = MyMovieRepository(wordsDao)
        allTvShow = repository.allTvShow
    }

    fun insertTvShow(tvShow: TvShowItem) = viewModelScope.launch {
        repository.insertTvShow(tvShow)
    }

    fun deleteTvShowById(idTvShow: Int) = viewModelScope.launch {
        repository.deleteTvShow(idTvShow)
    }
}