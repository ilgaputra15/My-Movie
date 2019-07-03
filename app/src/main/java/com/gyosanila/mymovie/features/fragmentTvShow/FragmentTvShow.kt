package com.gyosanila.mymovie.features.fragmentTvShow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.adapter.TvShowAdapter
import com.gyosanila.mymovie.features.movieDetail.MovieDetailActivity
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * Created by ilgaputra15
 * on Thursday, 27/06/2019 16:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentTvShow : Fragment(), FragmentTvShowContract.View {

    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var presenter: FragmentTvShowPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        presenter = FragmentTvShowPresenter(this)
        tvShowAdapter = TvShowAdapter { itemSelected: TvShowItem -> listTvShowClicked(itemSelected) }
        recyclerViewTvShow.layoutManager = GridLayoutManager(activity, 2)
        recyclerViewTvShow.adapter = tvShowAdapter
        getTvShowList()
    }

    private fun listTvShowClicked(itemSelected: TvShowItem) {
        val toMovieDetail = Intent(context!!, MovieDetailActivity::class.java)
        toMovieDetail.putExtra("Movie", itemSelected)
        startActivity(toMovieDetail)
    }

    override fun getTvShowList() {
        presenter.getTvShowList()
    }

    override fun setTvShowList(tvShowList: List<TvShowItem>) {
        tvShowAdapter.setListTvShow(tvShowList.toMutableList())
    }

    override fun setProgressBar(isShow: Boolean) {
        progressBar.visible = isShow
    }

    override fun showError(error: Throwable) {
        Toast.makeText(activity, "Fetch data error, ${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }
}