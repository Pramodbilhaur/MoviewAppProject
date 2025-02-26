package com.example.moviesappproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesappproject.activities.MovieDetails
import com.example.moviesappproject.databinding.ItemMovieThmBinding
import com.example.moviesappproject.databinding.SearchItemViewBinding
import com.example.moviesappproject.model.UpcomingResultList
import com.example.moviesappproject.network.NetworkingConstants

class SearchMovieAdapter(
    private var movies: List<UpcomingResultList>,
    private val onItemClick: (UpcomingResultList) -> Unit // Callback for item click
) : RecyclerView.Adapter<SearchMovieAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SearchItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            onItemClick(movie) // Notify activity to handle click
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<UpcomingResultList>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    class MyViewHolder(private val binding: SearchItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: UpcomingResultList) {
            binding.searchImage.load(NetworkingConstants.BASE_POSTER_PATH + movie.posterPath)
            binding.searchMovieName.text = movie.title
        }
    }
}
