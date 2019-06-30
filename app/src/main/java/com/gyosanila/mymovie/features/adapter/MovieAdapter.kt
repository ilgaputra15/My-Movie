package com.gyosanila.mymovie.features.adapter

import android.view.View
import android.view.ViewGroup
import com.gyosanila.mymovie.features.network.Movie
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.gyosanila.mymovie.R
import kotlinx.android.synthetic.main.item_movie.view.*


/**
 * Created by ilgaputra15
 * on Sunday, 23/06/2019 23:12
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class MovieAdapter(private val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var listMovie: ArrayList<Movie> = ArrayList()

    fun setListMovie(arrayList: ArrayList<Movie>) {
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
        fun bind(part: Movie) {
            itemView.textTitle.text = part.title
            itemView.textDescription.text = part.description
            itemView.imageMovie.setImageResource(part.photoResource)
        }
    }
}