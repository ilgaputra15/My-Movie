package com.gyosanila.mymovie.features.fragmentMovieFavorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.adapter.MovieAdapter
import com.gyosanila.mymovie.features.movieDetail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import com.gyosanila.mymovie.features.domain.network.MovieItem


/**
 * Created by ilgaputra15
 * on Thursday, 27/06/2019 16:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentMovieFavorites : Fragment(), FragmentMovieHistoryContract.View {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieViewModel: FragmentMovieViewModel

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
        movieAdapter = MovieAdapter { itemSelected: MovieItem -> listMovieClicked(itemSelected) }
        recyclerViewMovie.layoutManager = LinearLayoutManager(activity)
        recyclerViewMovie.adapter = movieAdapter
        movieViewModel = ViewModelProviders.of(this).get(FragmentMovieViewModel::class.java)
        movieViewModel.allMovie.observe(this, Observer { listMovie ->
            setMovieList(listMovie)
        })
    }

    private fun listMovieClicked(itemSelected: MovieItem) {
        val toMovieDetail = Intent(requireContext(), MovieDetailActivity::class.java)
        toMovieDetail.putExtra("Movie", itemSelected)
        startActivity(toMovieDetail)
    }

    override fun setMovieList(movieList: List<MovieItem>) {
        if (movieList.isEmpty()) textError.visible = true
        movieAdapter.setListMovie(movieList.toMutableList())
    }
}