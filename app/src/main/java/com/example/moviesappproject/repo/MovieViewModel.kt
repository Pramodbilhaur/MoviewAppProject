package com.example.moviesappproject.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappproject.model.GenreResultList
import com.example.moviesappproject.model.GetMovieDetailsResponse
import com.example.moviesappproject.model.TrailerResultList
import com.example.moviesappproject.model.UpcomingResultList
import com.example.moviesappproject.network.ApiService
import com.example.moviesappproject.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val apiService: ApiService,
    private val postRepository: MovieRepository
) : ViewModel() {
    val upcomingLiveData: MutableLiveData<List<UpcomingResultList>> = MutableLiveData()
    val popularLiveData: MutableLiveData<List<UpcomingResultList>> = MutableLiveData()
    val topRatedLiveData: MutableLiveData<List<UpcomingResultList>> = MutableLiveData()
    val genreLiveData: MutableLiveData<List<GenreResultList>> = MutableLiveData()
    val getTrailerLiveData: MutableLiveData<List<TrailerResultList>> = MutableLiveData()
    val movieDetailLivedata: MutableLiveData<GetMovieDetailsResponse> = MutableLiveData()
    val searchLiveData: MutableLiveData<List<UpcomingResultList>> = MutableLiveData()

    fun getUpcomingMovieResult() {
        viewModelScope.launch {
            postRepository.getUpcomingMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    upcomingLiveData.value = response.results
                }

        }
    }

    fun getPopularMovieResult() {
        viewModelScope.launch {
            postRepository.getPopularMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    popularLiveData.value = response.results
                }

        }
    }

    fun getTopRatedMovieResult() {
        viewModelScope.launch {
            postRepository.getTopRatedMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    topRatedLiveData.value = response.results
                }

        }
    }

    fun getGenreMovieResult() {
        viewModelScope.launch {
            postRepository.getGenreMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    genreLiveData.value = response.genres
                }

        }
    }

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            postRepository.getMovieDetails(movieId)
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    movieDetailLivedata.value = response
                }

        }
    }
    fun getTrailerResponse(movieId: String) {
        viewModelScope.launch {
            postRepository.getTrailerResponse(movieId)
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    getTrailerLiveData.value = response.results
                }

        }
    }

    fun clearSearchResults() {
        searchLiveData.value = emptyList()
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            postRepository.searchMovies(query)
                .catch { e ->
                    Log.d("main", "searchMovies: ${e.message}")
                }.collect { response ->
                    searchLiveData.value = response.results
                }
        }
    }

}