package com.gyosanila.mymovie.features.adapter

import android.view.View
import android.view.ViewGroup
import com.gyosanila.mymovie.features.domain.network.Movie
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.core.common.Constant
import com.gyosanila.mymovie.features.domain.network.MovieItem
import kotlinx.android.synthetic.main.item_movie.view.*


/**
 * Created by ilgaputra15
 * on Sunday, 23/06/2019 23:12
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class MovieAdapter(private val clickListener: (MovieItem) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var listMovie: MutableList<MovieItem> = ArrayList()

    fun setListMovie(arrayList: MutableList<MovieItem>) {
        listMovie = arrayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        val viewHolder = MovieHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            clickListener(listMovie[position])
        }
        return viewHolder
    }

    override fun getItemCount() = listMovie.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(listMovie[position])
    }


    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(part: MovieItem) {
            itemView.textTitle.text = part.title
            itemView.textDescription.text = part.overview
            Glide.with(itemView).load(Constant.ImageUrl+part.poster_path).into(itemView.imageMovie)
        }
    }
}