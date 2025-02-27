package com.example.moviesappproject

import com.example.moviesappproject.model.GenreResultList
import com.example.moviesappproject.model.GetMovieDetailsResponse
import com.example.moviesappproject.model.ProductionCountry
import com.example.moviesappproject.model.SpokenLanguage
import com.example.moviesappproject.network.ApiService
import com.example.moviesappproject.network.NetworkingConstants.API_KEY
import com.example.moviesappproject.repo.MovieRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    @Mock
    private lateinit var movieApiService: ApiService

    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        repository = MovieRepository(movieApiService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetch movie details successfully`() = runTest {
        val mockMovie = GetMovieDetailsResponse(
            adult = false,
            backdropPath = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
            belongsToCollection = Any(), // Replace with actual collection data if available
            budget = 165000000,
            genres = listOf(
                GenreResultList(28, "Adventure"),
                GenreResultList(12, "Drama"),
                GenreResultList(878, "Science Fiction")
            ),
            homepage = "https://www.interstellarmovie.net/",
            id = 157336,
            imdbId = "tt0816692",
            originalLanguage = "en",
            originalTitle = "Interstellar",
            overview = "The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
            popularity = 123.939,
            posterPath = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
            productionCompanies = listOf(), // Replace with actual company data if needed
            productionCountries = listOf(ProductionCountry("US", "United States of America")),
            releaseDate = "2014-11-05",
            revenue = 677471339,
            runtime = 169,
            spokenLanguages = listOf(SpokenLanguage("English", "en", "Interstellar")),
            status = "Released",
            tagline = "Mankind was born on Earth. It was never meant to die here.",
            title = "Interstellar",
            video = false,
            voteAverage = 8.451,
            voteCount = 36584
        )

        `when`(movieApiService.getMovieDetails("157336", API_KEY)).thenReturn(flowOf(mockMovie).first())

        val result = repository.getMovieDetails("157336").first() // Collect first emitted value

        assertEquals(mockMovie, result)
    }

    @Test
    fun `fetch movie details failure`() = runTest {
        `when`(movieApiService.getMovieDetails("1", API_KEY)).thenThrow(RuntimeException("API Error"))

        try {
            repository.getMovieDetails("1").first()
            fail("Expected an exception")
        } catch (e: Exception) {
            assertEquals("API Error", e.message)
        }
    }
}
