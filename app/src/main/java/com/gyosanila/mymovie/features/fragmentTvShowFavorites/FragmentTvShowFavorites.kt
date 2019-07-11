package com.gyosanila.mymovie.features.fragmentTvShowFavorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.adapter.TvShowAdapter
import com.gyosanila.mymovie.features.domain.network.TvShowItem
import com.gyosanila.mymovie.features.tvShowDetail.TvShowDetailActivity
import kotlinx.android.synthetic.main.fragment_movie.progressBar
import kotlinx.android.synthetic.main.fragment_tv_show.*


/**
 * Created by ilgaputra15
 * on Thursday, 27/06/2019 16:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentTvShowFavorites : Fragment(), FragmentTvShowFavoritesContract.View {

    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var tvShowFavoritesViewModel: FragmentTvShowFavoritesViewModel

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
        tvShowAdapter = TvShowAdapter { itemSelected: TvShowItem -> listTvShowClicked(itemSelected) }
        recyclerViewTvShow.layoutManager = GridLayoutManager(activity, 2)
        recyclerViewTvShow.adapter = tvShowAdapter
        tvShowFavoritesViewModel = ViewModelProviders.of(this).get(FragmentTvShowFavoritesViewModel::class.java)
        tvShowFavoritesViewModel.allTvShow.observe(this, Observer { listTvShow ->
            setListTvShow(listTvShow)
        })
    }

    private fun listTvShowClicked(itemSelected: TvShowItem) {
        val toMovieDetail = Intent(requireContext(), TvShowDetailActivity::class.java)
        toMovieDetail.putExtra("TvShow", itemSelected)
        startActivity(toMovieDetail)
    }

    override fun setListTvShow(listTvShow: List<TvShowItem>) {
        if (listTvShow.isEmpty()) textError.visible = true
        tvShowAdapter.setListTvShow(listTvShow.toMutableList())
    }
}