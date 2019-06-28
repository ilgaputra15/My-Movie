package com.gyosanila.mymovie.features.fragmentTvShow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.adapter.TvShowAdapter
import com.gyosanila.mymovie.features.movieDetail.MovieDetailActivity
import com.gyosanila.mymovie.features.network.Movie
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * Created by ilgaputra15
 * on Thursday, 27/06/2019 16:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

class FragmentTvShow : Fragment() {

    private val listTvShow = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val tvShowAdapter = TvShowAdapter { itemSelected: Movie -> listTvShowClicked(itemSelected) }
        recyclerViewTvShow.layoutManager = GridLayoutManager(activity, 2)
        recyclerViewTvShow.adapter = tvShowAdapter
        tvShowAdapter.setListTvShow(seListTvShow())
    }

    private fun listTvShowClicked(itemSelected: Movie) {
        val toMovieDetail = Intent(context!!, MovieDetailActivity::class.java)
        toMovieDetail.putExtra("Movie", itemSelected)
        startActivity(toMovieDetail)
    }

    @SuppressLint("Recycle")
    fun seListTvShow(): ArrayList<Movie> {
        val dataTitle = resources.getStringArray(R.array.data_title_tv_show)
        val dataDescription = resources.getStringArray(R.array.data_description_tv_show)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo_tv_show)
        val dataPublish = resources.getStringArray(R.array.data_publish_at_tv_show)
        val dataDirector = resources.getStringArray(R.array.data_director_tv_show)
        for (data in 0 until dataTitle.size) {
            val hero = Movie(
                data,
                dataTitle[data],
                dataDescription[data],
                dataPhoto.getResourceId(data, -1),
                dataPublish[data],
                dataDirector[data]
            )
            listTvShow.add(hero)
        }
        return listTvShow
    }
}