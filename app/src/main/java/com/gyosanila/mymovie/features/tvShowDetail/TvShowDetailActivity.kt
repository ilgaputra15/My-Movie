package com.gyosanila.mymovie.features.tvShowDetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.domain.network.TvShowDetail
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity(), TvShowDetailContract.View {

    private lateinit var tvShowItem: TvShowItem
    private lateinit var presenter: TvShowDetailPresenter
    private lateinit var tvShowDetail: TvShowDetail
    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var menu: Menu
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        setupUI()
        if (savedInstanceState == null) {
            getDataIntent()
        } else {
            tvShowDetail = savedInstanceState.getParcelable("tvShowDetail")
            showTvShowDetail(tvShowDetail)
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
                    tvShowViewModel.deleteTvShowById(tvShowItem.id)
                    setIconFavorite(false)
                } else {
                    tvShowViewModel.insertTvShow(tvShowItem)
                    setIconFavorite(true)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDataIntent() {
        tvShowItem = intent.getParcelableExtra("TvShow")
        getTvShowDetail()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        presenter = TvShowDetailPresenter(this)
        scrollView.visible = false
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        tvShowViewModel.allTvShow.observe(this, Observer { listTvShow ->
            setFavorite(listTvShow)
        })
    }

    private fun setFavorite(listTvShow: List<TvShowItem>) {
        if (listTvShow.isNotEmpty())
            for (item in listTvShow) {
                if (item.id == tvShowItem.id) setIconFavorite(true)
            }
    }

    private fun setIconFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        val icon = if (isFavorite) getDrawable(R.drawable.ic_outline_favorite_full)
        else getDrawable(R.drawable.ic_outline_favorite)
        if (::menu.isInitialized) menu.getItem(0).icon = icon
    }

    override fun getTvShowDetail() {
        presenter.getTvShowDetail(tvShowItem.id)
    }

    @SuppressLint("SetTextI18n")
    override fun showTvShowDetail(tvShowDetail: TvShowDetail) {
        this.tvShowDetail = tvShowDetail
        textTitle.text = tvShowDetail.name
        Glide.with(this)
            .load(Constant.ImageUrl+tvShowDetail.poster_path)
            .into(imageTvShow)
        textPublishAt.text = "(${tvShowDetail.first_air_date})"
        textValueGenres.text = tvShowDetail.genres.joinToString { it.name + " " }
        textValueOverview.text = tvShowDetail.overview
        textValueCreatedBy.text = tvShowDetail.created_by.joinToString { it.name + " " }
        textValuePopularity.text = tvShowDetail.popularity.toString()
        textValueVoteAverage.text = tvShowDetail.vote_average.toString()
        textValueSession.text = tvShowDetail.seasons.size.toString()
        textValueHomePage.let {
            it.text = tvShowDetail.homepage
            it.movementMethod = LinkMovementMethod.getInstance()
        }
        scrollView.visible = true
    }

    override fun setProgressBar(isShow: Boolean) {
        progressBar.visible = isShow
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this, "Fetch data error, ${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::tvShowDetail.isInitialized) outState.putParcelable("tvShowDetail", tvShowDetail)
    }
}
