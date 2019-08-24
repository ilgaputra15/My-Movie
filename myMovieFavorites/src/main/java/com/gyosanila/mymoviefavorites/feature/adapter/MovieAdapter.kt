package com.gyosanila.mymoviefavorites.feature.adapter

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gyosanila.mymoviefavorites.R
import com.gyosanila.mymoviefavorites.core.Constant
import com.gyosanila.mymoviefavorites.feature.domain.network.MovieFavorites
import kotlinx.android.synthetic.main.item_movie.view.*


/**
 * Created by ilgaputra15
 * on Sunday, 23/06/2019 23:12
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var listMovie: MutableList<MovieFavorites> = ArrayList()

    fun setListMovie(arrayList: MutableList<MovieFavorites>) {
        listMovie = arrayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount() = listMovie.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(listMovie[position])
    }


    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(part: MovieFavorites) {
            itemView.textTitle.text = part.title
            itemView.textDescription.text = part.overview
            Glide.with(itemView).load(Constant.ImageUrl+part.photoPostUrl).into(itemView.imageMovie)
        }
    }
}