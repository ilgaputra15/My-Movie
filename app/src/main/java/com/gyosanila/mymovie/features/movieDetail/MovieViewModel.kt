package com.gyosanila.mymovie.features.movieDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gyosanila.mymovie.features.domain.local.MyMovieRepository
import com.gyosanila.mymovie.features.domain.local.MyMovieRoomDatabase
import com.gyosanila.mymovie.features.domain.network.MovieItem
import kotlinx.coroutines.launch


class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyMovieRepository
    val allMovies: LiveData<List<MovieItem>>

    init {
        val wordsDao = MyMovieRoomDatabase.getDatabase(application).movieDao()
        repository = MyMovieRepository(wordsDao)
        allMovies = repository.allMovies
    }

    fun insert(movie: MovieItem) = viewModelScope.launch {
        repository.insertMovie(movie)
    }

    fun deleteMovieById(idMovie: Int) = viewModelScope.launch {
        repository.deleteMovie(idMovie)
    }
}
