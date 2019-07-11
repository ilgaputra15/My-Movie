package com.gyosanila.mymovie.features.domain.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.gyosanila.mymovie.features.domain.network.MovieItem
import com.gyosanila.mymovie.features.domain.network.TvShowItem

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [MovieItem::class, TvShowItem::class], version = 1)
abstract class MyMovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MyMovieDao

    companion object {

        @Volatile
        private var INSTANCE: MyMovieRoomDatabase? = null

        fun getDatabase(context: Context): MyMovieRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyMovieRoomDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
