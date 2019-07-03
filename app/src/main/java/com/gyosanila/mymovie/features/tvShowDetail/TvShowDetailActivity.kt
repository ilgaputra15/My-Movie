package com.gyosanila.mymovie.features.tvShowDetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.domain.network.TvShowDetail
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity(), TvShowDetailContract.View {

    private lateinit var tvShowDetail: TvShowItem
    private lateinit var presenter: TvShowDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        setupUI()
        getDataIntent()
    }

    private fun getDataIntent() {
        tvShowDetail = intent.getParcelableExtra("TvShow")
        getTvShowDetail()
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        presenter = TvShowDetailPresenter(this)
        scrollView.visible = false
    }

    override fun getTvShowDetail() {
        presenter.getTvShowDetail(tvShowDetail.id)
    }


    @SuppressLint("SetTextI18n")
    override fun showTvShowDetail(tvShowDetail: TvShowDetail) {
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
}
