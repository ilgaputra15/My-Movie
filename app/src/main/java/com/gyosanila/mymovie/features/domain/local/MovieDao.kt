package com.gyosanila.mymovie.features.domain.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gyosanila.mymovie.features.domain.network.MovieItem

/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 00:46
 * Division Mobile - PT.Homecareindo Global Medika
 **/

@Dao
interface MovieDao {

    @Query("SELECT * from movie_table ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<MovieItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: MovieItem)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("DELETE FROM movie_table WHERE id = :idMovie")
    suspend fun deleteMovieById(idMovie: Int)

    @Query("SELECT * from movie_table WHERE id = :idMovie")
    fun getMovieById(idMovie: Int): LiveData<MovieItem>


}