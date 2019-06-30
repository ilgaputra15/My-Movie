package com.gyosanila.mymovie.features.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gyosanila.mymovie.R
import com.gyosanila.mymovie.features.network.Movie
import kotlinx.android.synthetic.main.item_tv_show.view.*

/**
 * Created by ilgaputra15
 * on Thursday, 27/06/2019 16:54
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class TvShowAdapter(private val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<TvShowAdapter.TvShowHolder>() {

    private var listTvShow: ArrayList<Movie> = ArrayList()

    fun setListTvShow(arrayList: ArrayList<Movie>) {
        listTvShow = arrayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        val viewHolder = TvShowHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            clickListener(listTvShow[position])
        }
        return viewHolder
    }

    override fun getItemCount() = listTvShow.size

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {
        holder.bind(listTvShow[position])
    }


    class TvShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(part: Movie) {
            itemView.imageTvShow.setImageResource(part.photoResource)
        }
    }
}