package com.example.moviesappproject.network

import com.example.moviesappproject.model.GetGenresResponse
import com.example.moviesappproject.model.GetMovieDetailsResponse
import com.example.moviesappproject.model.GetMovieListResponse
import com.example.moviesappproject.model.GetTrailerResponse
import com.example.moviesappproject.model.GetUpcomingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET(NetworkingConstants.DISCOVER_MOVIE)
    suspend fun getDiscoverMovie(
        @Query("api_key") api_key: String, @Query("page") page: Int
    ): GetMovieListResponse


    @GET(NetworkingConstants.UPCOMING_MOVIE)
    suspend fun getUpcomingMovie(
        @Query("api_key") api_key: String, @Query("page") page: String
    ): GetUpcomingMovieResponse

    @GET(NetworkingConstants.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("api_key") api_key: String, @Query("page") page: String
    ): GetUpcomingMovieResponse

    @GET(NetworkingConstants.TOP_RATED_MOVIE)
    suspend fun getTopRatedMovie(
        @Query("api_key") api_key: String, @Query("page") page: String
    ): GetUpcomingMovieResponse


    @GET(NetworkingConstants.GENRE_MOVIE)
    suspend fun getGenreMovie(
        @Query("api_key") api_key: String
    ): GetGenresResponse


    @GET(NetworkingConstants.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("movieId") movieId: String,
        @Query("api_key") api_key: String
    ): GetMovieDetailsResponse

    @GET(NetworkingConstants.TRAILER_VIDEO)
    suspend fun getTrailerResponse(
        @Path("movieId") movieId: String,
        @Query("api_key") api_key: String
    ): GetTrailerResponse

    @GET(NetworkingConstants.SEARCH_MOVIE)
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): GetUpcomingMovieResponse
}
