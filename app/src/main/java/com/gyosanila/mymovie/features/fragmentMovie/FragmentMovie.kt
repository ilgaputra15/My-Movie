package com.gyosanila.mymovie.features.fragmentMovie

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.adapter.MovieAdapter
import com.gyosanila.mymovie.features.movieDetail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import com.gyosanila.mymovie.features.domain.network.MovieItem
import android.widget.SearchView
import androidx.core.content.ContextCompat

/**
 * Created by ilgaputra15
 * on Thursday, 27/06/2019 16:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentMovie : Fragment(), FragmentMovieContract.View {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var presenter: FragmentMoviePresenter
    private lateinit var movieList: ArrayList<MovieItem>

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
        if (savedInstanceState == null) {
            getListMovie()
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movie")
            setMovieList(movieList)
        }
    }

    override fun setProgressBar(isShow: Boolean) {
        recyclerViewMovie.visible = !isShow
        progressBar.visible = isShow
    }

    private fun setupUI() {
        presenter = FragmentMoviePresenter(this)
        movieAdapter = MovieAdapter { itemSelected: MovieItem -> listMovieClicked(itemSelected) }
        recyclerViewMovie.layoutManager = LinearLayoutManager(activity)
        recyclerViewMovie.adapter = movieAdapter
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        swipeRefresh.setOnRefreshListener {
            getListMovie()
            searchView.setQuery("", false)
            swipeRefresh.isRefreshing = false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean { return true }
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMovie(query)
                return true
            }
        })
        searchView.setOnCloseListener {
            getListMovie()
            false
        }
    }

    override fun searchMovie(query: String) {
        presenter.searchMovie(query)
    }

    override fun getListMovie() {
        presenter.getListMovie()
    }

    private fun listMovieClicked(itemSelected: MovieItem) {
        val toMovieDetail = Intent(requireContext(), MovieDetailActivity::class.java)
        toMovieDetail.putExtra("Movie", itemSelected)
        startActivity(toMovieDetail)
    }

    override fun setMovieList(movieList: List<MovieItem>) {
        textError.visible = movieList.isEmpty()
        this.movieList = movieList as ArrayList<MovieItem>
        movieAdapter.setListMovie(this.movieList)
    }

    override fun setSearchMovie(movieList: List<MovieItem>) {
        textError.visible = movieList.isEmpty()
        if (movieList.isEmpty()) textError.text = getString(R.string.text_not_found_search)
        this.movieList = movieList as ArrayList<MovieItem>
        movieAdapter.setListMovie(this.movieList)
    }

    override fun showError(error: Throwable) {
        textError.visible = true
        Toast.makeText(activity, "Fetch data error, ${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("movie", movieList)
    }
}