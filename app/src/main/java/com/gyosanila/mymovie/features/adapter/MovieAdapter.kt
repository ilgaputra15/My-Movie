package com.gyosanila.mymovie.features.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gyosanila.mymovie.features.network.Movie
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
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
        return MovieHolder(view)
    }

    override fun getItemCount() = listMovie.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(listMovie[position], clickListener)
    }


    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(part: Movie, clickListener: (Movie) -> Unit) {
            itemView.textTitle.text = part.title
            itemView.textDescription.text = part.description
            itemView.imageMovie.setImageResource(part.photoResource)
            itemView.setOnClickListener {
                clickListener(part)
            }
        }
    }
}