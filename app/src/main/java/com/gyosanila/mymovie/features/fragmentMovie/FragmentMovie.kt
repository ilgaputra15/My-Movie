package com.gyosanila.mymovie.features.fragmentMovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.adapter.MovieAdapter
import com.gyosanila.mymovie.features.movieDetail.MovieDetailActivity
import com.gyosanila.mymovie.features.network.Movie
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * Created by ilgaputra15
 * on Thursday, 27/06/2019 16:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentMovie : Fragment() {

    private val listMovie = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val movieAdapter = MovieAdapter { itemSelected: Movie -> listMovieClicked(itemSelected) }
        recyclerViewMovie.layoutManager = LinearLayoutManager(activity)
        recyclerViewMovie.adapter = movieAdapter
        movieAdapter.setListMovie(setMovieList())
    }

    private fun listMovieClicked(itemSelected: Movie) {
        val toMovieDetail = Intent(context!!, MovieDetailActivity::class.java)
        toMovieDetail.putExtra("Movie", itemSelected)
        startActivity(toMovieDetail)
    }

    @SuppressLint("Recycle")
    fun setMovieList(): ArrayList<Movie> {
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataPublish = resources.getStringArray(R.array.data_publish_at)
        val dataDirector = resources.getStringArray(R.array.data_director)
        for (data in 0 until dataTitle.size) {
            val hero = Movie(
                data,
                dataTitle[data],
                dataDescription[data],
                dataPhoto.getResourceId(data, -1),
                dataPublish[data],
                dataDirector[data]
            )
            listMovie.add(hero)
        }
        return listMovie
    }
}