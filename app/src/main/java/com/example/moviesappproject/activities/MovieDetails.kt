package com.example.moviesappproject.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.moviesappproject.R
import com.example.moviesappproject.utlis.ProgressBarHandler
import com.example.moviesappproject.adapter.TrailerAdapter
import com.example.moviesappproject.databinding.ActivityMovieDetailsBinding
import com.example.moviesappproject.model.MovieEntity
import com.example.moviesappproject.network.NetworkingConstants
import com.example.moviesappproject.repo.FavoriteRepository
import com.example.moviesappproject.repo.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetails : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private val mainViewModel: MovieViewModel by viewModels()
    private lateinit var favoriteRepository: FavoriteRepository
    private var movieId: String? = null
    private var progressBar: ProgressBarHandler? = null
    private var isFavorite = false
    private var currentMovie: MovieEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteRepository = FavoriteRepository(this)

        initProgress()
        initApiCalls()
        setupFavoriteButton()
    }

    private fun initApiCalls() {
        movieId = intent.getStringExtra("MovieIdPass").toString()
        mainViewModel.getMovieDetails(movieId!!)
        mainViewModel.getTrailerResponse(movieId!!)

        binding.backButton.setOnClickListener {
            finish()
        }

        mainViewModel.movieDetailLivedata.observe(this@MovieDetails, Observer { response ->
            progressBar?.hide()
            binding.apply {
                movieBackdropImage.load(NetworkingConstants.BASE_BACKDROP_PATH + response.backdropPath)
                moviePosterImage.load(NetworkingConstants.BASE_POSTER_PATH + response.posterPath)
                movieTitleText.text = response.title
                movieOverviewText.text = response.overview
                movieInfoText.text = response.releaseDate
            }

            currentMovie = MovieEntity(
                id = response.id,
                title = response.title,
                posterPath = response.posterPath,
                releaseDate = response.releaseDate
            )

            lifecycleScope.launch {
                isFavorite = favoriteRepository.isFavorite(response.id)
                updateFavoriteIcon()
            }
        })

        mainViewModel.getTrailerLiveData.observe(this, Observer {
            it?.let {
                binding.apply {
                    recyclerviewTrailer.apply {
                        recyclerviewTrailer.adapter =
                            TrailerAdapter(it) {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(NetworkingConstants.YOUTUBE_VIDEO_URL + it.key)
                                startActivity(intent)
                            }
                        layoutManager = LinearLayoutManager(
                            this@MovieDetails,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    }
                }
            }
        })
    }

    private fun setupFavoriteButton() {
        binding.favoriteIcon.setOnClickListener {
            lifecycleScope.launch {
                toggleFavorite()
            }
        }
    }

    private suspend fun toggleFavorite() {
        currentMovie?.let { movie ->
            if (isFavorite) {
                favoriteRepository.removeFavorite(movie)
                showToast("${movie.title} has been removed from your favorites.")
            } else {
                favoriteRepository.addFavorite(movie)
                showToast("${movie.title} has been added to your favorites.")
            }
            isFavorite = !isFavorite
            updateFavoriteIcon()
        }
    }

    private fun updateFavoriteIcon() {
        val iconRes = if (isFavorite) R.drawable.favorite_star_svgrepo_com else R.drawable.favorite_svgrepo_com
        binding.favoriteIcon.setImageResource(iconRes)
    }

    private fun initProgress() {
        progressBar = ProgressBarHandler(this)
        progressBar!!.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

/*@AndroidEntryPoint
class MovieDetails : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private val mainViewModel: MovieViewModel by viewModels()
    var movieId: String? = null
    var progressBar: ProgressBarHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProgress();
        initApiCalls();


    }

    private fun initApiCalls() {
        movieId = intent.getStringExtra("MovieIdPass").toString()
        mainViewModel.getMovieDetails(movieId!!)
        mainViewModel.getTrailerResponse(movieId!!)
        binding.backButton.setOnClickListener {
            finish()
        }


        mainViewModel.movieDetailLivedata.observe(this@MovieDetails, Observer { response ->
            progressBar?.hide()
            binding.apply {

                movieBackdropImage.load(NetworkingConstants.BASE_BACKDROP_PATH + response.backdropPath)
                moviePosterImage.load(NetworkingConstants.BASE_POSTER_PATH + response.posterPath)
                movieTitleText.text = response.title
                movieOverviewText.text = response.overview
                movieInfoText.text = response.releaseDate
            }
        })
        mainViewModel.getTrailerLiveData.observe(this, Observer {
            it?.let {
                binding.apply {
                    recyclerviewTrailer.apply {
                        recyclerviewTrailer.adapter =
                            TrailerAdapter(
                                it
                            ) {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data =
                                    Uri.parse(NetworkingConstants.YOUTUBE_VIDEO_URL + it.key)
                                startActivity(intent)
                            }
                        layoutManager = LinearLayoutManager(
                            this@MovieDetails,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                    }

                }
            }
        })
    }

    private fun initProgress() {
        progressBar = ProgressBarHandler(this)
        progressBar!!.show()
    }


}*/

