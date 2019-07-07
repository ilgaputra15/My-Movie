package com.gyosanila.mymovie.features.movieDetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.domain.network.MovieDetail
import com.gyosanila.mymovie.features.domain.network.MovieItem
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {

    private lateinit var movieItem: MovieItem
    private lateinit var presenter: MovieDetailPresenter
    private lateinit var movieDetail: MovieDetail
    private lateinit var wordViewModel: MovieViewModel
    private lateinit var menu: Menu
    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupUI()
        if (savedInstanceState == null) {
            getDataIntent()
        } else {
            movieDetail = savedInstanceState.getParcelable("movieDetail")
            showMovieDetail(movieDetail)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.toolbar_detail, menu)
        setIconFavorite(isFavorite)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                if (isFavorite) {
                    wordViewModel.deleteMovieById(movieItem.id)
                    setIconFavorite(false)
                } else {
                    wordViewModel.insert(movieItem)
                    setIconFavorite(true)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDataIntent() {
        movieItem = intent.getParcelableExtra("Movie")
        getMovieDetail()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        presenter = MovieDetailPresenter(this)
        scrollView.visible = false
        wordViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        wordViewModel.allMovies.observe(this, Observer { listMovie ->
            setFavorite(listMovie)
        })
    }

    private fun setFavorite(listMovie: List<MovieItem>) {
        if (listMovie.isNotEmpty())
            for (item in listMovie) {
                if (item.id == movieItem.id) setIconFavorite(true)
            }
    }

    private fun setIconFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        val icon = if (isFavorite) getDrawable(R.drawable.ic_outline_favorite_full)
        else getDrawable(R.drawable.ic_outline_favorite)
        if (::menu.isInitialized) menu.getItem(0).icon = icon
    }

    override fun getMovieDetail() {
        presenter.getMovieDetail(movieItem.id)
    }

    @SuppressLint("SetTextI18n")
    override fun showMovieDetail(movieDetail: MovieDetail) {
        this.movieDetail = movieDetail
        textTitle.text = movieDetail.title
        Glide.with(this)
            .load(Constant.ImageUrl+movieDetail.poster_path)
            .into(imageMovie)
        textPublishAt.text = "(${movieDetail.release_date})"
        textValueLanguage.text = movieDetail.spoken_languages.joinToString { it.name + " " }
        textValueOverview.text = movieDetail.overview
        textValueBudget.text = formatPrice(movieDetail.budget)
        textValueRevenue.text = formatPrice(movieDetail.revenue)
        textValuePopularity.text = movieDetail.popularity.toString()
        textValueVoteAverage.text = movieDetail.vote_average.toString()
        textValueRunTime.text = movieDetail.runtime.toString()
        scrollView.visible = true
    }

    override fun setProgressBar(isShow: Boolean) {
        progressBar.visible = isShow
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this, "Fetch data error, ${error.message}", Toast.LENGTH_SHORT).show()
    }

    private fun formatPrice(price: Long) : String {
        val priceFormat = DecimalFormat()
        val formatter = DecimalFormatSymbols()
        formatter.groupingSeparator ='.'
        formatter.decimalSeparator=','
        priceFormat.decimalFormatSymbols = formatter
        return priceFormat.format(price)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::movieDetail.isInitialized) outState.putParcelable("movieDetail", movieDetail)
    }
}
