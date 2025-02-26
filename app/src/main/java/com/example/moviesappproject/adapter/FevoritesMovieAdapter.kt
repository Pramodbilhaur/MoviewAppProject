package com.example.moviesappproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviesappproject.activities.MovieDetails
import com.example.moviesappproject.databinding.ItemMovieThmBinding
import com.example.moviesappproject.model.MovieEntity
import com.example.moviesappproject.network.NetworkingConstants

class FevoritesMovieAdapter(val ctx: Context, val movies: List<MovieEntity>) :
    RecyclerView.Adapter<FevoritesMovieAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder = MyViewHolder(
        ItemMovieThmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val movie: MovieEntity = movies[position]
        if (movie != null) {
            viewHolder.binds(movie)
        }


        viewHolder.itemView.setOnClickListener {
            val intent = Intent(ctx, MovieDetails::class.java)
            val movieId: String = movie.id.toString()
            intent.putExtra("MovieIdPass", movieId)
            ctx.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MyViewHolder(private val binding: ItemMovieThmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(moiveResultList: MovieEntity) {
            binding.apply {

                movieImage.load(NetworkingConstants.BASE_POSTER_PATH + moiveResultList.posterPath)
                movieTitle.text = moiveResultList.title
                year.text = moiveResultList.releaseDate

            }
        }
    }


}