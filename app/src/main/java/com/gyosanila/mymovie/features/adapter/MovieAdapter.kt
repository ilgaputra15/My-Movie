package com.gyosanila.mymovie.features.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gyosanila.mymovie.features.network.Movie
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.gyosanila.mymovie.R
import kotlinx.android.synthetic.main.item_movie.view.*


/**
 * Created by ilgaputra15
 * on Sunday, 23/06/2019 23:12
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class MovieAdapter(private val context: Context, private val movie: ArrayList<Movie>) : BaseAdapter() {

    override fun getCount() = movie.size

    override fun getItem(position: Int) = movie[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.run { inflate(R.layout.item_movie, parent, false) }
        val viewHolder = ViewHolder(view)
        val hero = getItem(position)
        viewHolder.bind(hero)
        return view
    }

    class ViewHolder(itemView: View) {
        private val image = itemView.imageMovie
        private val title = itemView.textTitle
        private val description = itemView.textDescription
        fun bind(movie: Movie) {
            title.text = movie.title
            description.text = movie.description
            image.setImageResource(movie.photoResource)
        }
    }
}