package com.gyosanila.mymovie.features.fragmentMovieFavorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gyosanila.mymovie.features.domain.local.MyMovieRepository
import com.gyosanila.mymovie.features.domain.local.MyMovieRoomDatabase
import com.gyosanila.mymovie.features.domain.network.MovieItem

/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 21:30
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentMovieViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: MyMovieRepository
    val allMovie: LiveData<List<MovieItem>>

    init {
        val wordsDao = MyMovieRoomDatabase.getDatabase(application).movieDao()
        repository = MyMovieRepository(wordsDao)
        allMovie = repository.allMovies
    }
}