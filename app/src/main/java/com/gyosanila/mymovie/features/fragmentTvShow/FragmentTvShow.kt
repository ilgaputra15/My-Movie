package com.gyosanila.mymovie.features.fragmentTvShow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.extension.visible
import com.gyosanila.mymovie.features.adapter.TvShowAdapter
import com.gyosanila.mymovie.features.tvShowDetail.TvShowDetailActivity
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
    private lateinit var tvShowList: ArrayList<TvShowItem>

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
        if (savedInstanceState == null) {
            getTvShowList()
        } else {
            tvShowList = savedInstanceState.getParcelableArrayList("tvShow")
            setTvShowList(tvShowList)
        }
    }

    private fun setupUI() {
        presenter = FragmentTvShowPresenter(this)
        tvShowAdapter = TvShowAdapter { itemSelected: TvShowItem -> listTvShowClicked(itemSelected) }
        recyclerViewTvShow.layoutManager = GridLayoutManager(activity, 2)
        recyclerViewTvShow.adapter = tvShowAdapter
        progressBar.visible = false
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
        swipeRefresh.setOnRefreshListener {
            getTvShowList()
            searchView.setQuery("", false)
            swipeRefresh.isRefreshing = false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String): Boolean { return true }
            override fun onQueryTextSubmit(query: String): Boolean {
                searchTvShow(query)
                return true
            }
        })
        searchView.setOnCloseListener {
            getTvShowList()
            false
        }
    }

    override fun getTvShowList() {
        presenter.getTvShowList()
    }

    override fun searchTvShow(query: String) {
        presenter.searchTvShow(query)
    }

    override fun setTvShowList(tvShowList: List<TvShowItem>) {
        textError.visible = tvShowList.isEmpty()
        this.tvShowList = tvShowList as ArrayList<TvShowItem>
        tvShowAdapter.setListTvShow(this.tvShowList)
    }

    override fun setSearchTvShow(tvShowList: List<TvShowItem>) {
        textError.visible = tvShowList.isEmpty()
        this.tvShowList = tvShowList as ArrayList<TvShowItem>
        if (tvShowList.isEmpty()) textError.text = getString(R.string.text_not_found_search)
        tvShowAdapter.setListTvShow(this.tvShowList)
    }

    private fun listTvShowClicked(itemSelected: TvShowItem) {
        val toMovieDetail = Intent(requireContext(), TvShowDetailActivity::class.java)
        toMovieDetail.putExtra("TvShow", itemSelected)
        startActivity(toMovieDetail)
    }

    override fun setProgressBar(isShow: Boolean) {
        recyclerViewTvShow.visible = !isShow
        progressBar.visible = isShow
    }

    override fun showError(error: Throwable) {
        Toast.makeText(activity, "Fetch data error, ${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!tvShowList.isNullOrEmpty()) outState.putParcelableArrayList("tvShow", tvShowList)
    }
}