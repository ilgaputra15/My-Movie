package com.gyosanila.mymovie.features.domain.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gyosanila.mymovie.features.domain.network.MovieItem
import com.gyosanila.mymovie.features.domain.network.TvShowItem

/**
 * Created by ilgaputra15
 * on Sunday, 07/07/2019 00:46
 * Division Mobile - PT.Homecareindo Global Medika
 **/

@Dao
interface MyMovieDao {

    @Query("SELECT * from movie_table ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<MovieItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(word: MovieItem)

    @Query("DELETE FROM movie_table WHERE id = :idMovie")
    suspend fun deleteMovieById(idMovie: Int)

    @Query("SELECT * from movie_table WHERE id = :idMovie")
    fun getMovieById(idMovie: Int): LiveData<MovieItem>

    @Query("DELETE FROM tv_show_table")
    fun deleteAllTvShow()


    @Query("SELECT * from tv_show_table ORDER BY id ASC")
    fun getAllTvShow(): LiveData<List<TvShowItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(word: TvShowItem)

    @Query("DELETE FROM tv_show_table WHERE id = :idMovie")
    suspend fun deleteTvShowById(idMovie: Int)

    @Query("SELECT * from tv_show_table WHERE id = :idMovie")
    fun getTvShowById(idMovie: Int): LiveData<TvShowItem>

    @Query("DELETE FROM tv_show_table")
    fun deleteAllMovie()




}