package com.example.moviesappproject.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappproject.activities.MovieDetails
import com.example.moviesappproject.databinding.ItenGenreBinding
import com.example.moviesappproject.model.GenreResultList


class GenreAdapter(val ctx: Context, val movies: List<GenreResultList>) :
    RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder = MyViewHolder(
        ItenGenreBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val movie: GenreResultList = movies[position]
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

    class MyViewHolder(private val binding: ItenGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(moiveResultList: GenreResultList) {
            binding.apply {

                title.text = moiveResultList.name

            }
        }
    }


}