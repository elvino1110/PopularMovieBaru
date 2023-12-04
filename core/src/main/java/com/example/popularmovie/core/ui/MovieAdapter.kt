package com.example.popularmovie.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovie.core.R
import com.example.popularmovie.core.domain.model.Movie
import com.example.popularmovie.core.utils.withDateFormat
import com.example.popularmovie.core.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem
        }
    }

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = listData.size
            override fun getNewListSize() = newListData.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                listData[oldItemPosition].id == newListData[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                listData[oldItemPosition] == newListData[newItemPosition]
        })

        listData.clear()
        listData.addAll(newListData)

        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)
        fun bind(data: Movie) {
            with(binding) {
                tvTitle.text = data.title
                tvOverview.text = data.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${data.posterPath}")
                    .placeholder(R.drawable.baseline_image_24)
                    .into(ivImage)
                tvPopularity.text = data.popularity.toString()
                tvVote.text = data.voteCount.toString()
                tvDate.text = data.releaseDate.withDateFormat()
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }


}