package com.example.moviesappproject.repo

import android.content.Context
import com.example.moviesappproject.local.MovieDatabase
import com.example.moviesappproject.model.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository(context: Context) {
    private val movieDao = MovieDatabase.getDatabase(context).movieDao()

    suspend fun addFavorite(movie: MovieEntity) = withContext(Dispatchers.IO) {
        movieDao.insertMovie(movie)
    }

    suspend fun removeFavorite(movie: MovieEntity) = withContext(Dispatchers.IO) {
        movieDao.deleteMovie(movie)
    }

    suspend fun getFavorites(): List<MovieEntity> = withContext(Dispatchers.IO) {
        movieDao.getAllFavorites()
    }

    suspend fun isFavorite(movieId: Int): Boolean = withContext(Dispatchers.IO) {
        movieDao.isFavorite(movieId)
    }
}
