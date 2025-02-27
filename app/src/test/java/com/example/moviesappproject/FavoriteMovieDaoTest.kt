package com.example.moviesappproject

import com.example.moviesappproject.local.MovieDao
import com.example.moviesappproject.model.MovieEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class FavoriteMovieDaoTest {

    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        // Mock the MovieDao
        dao = Mockito.mock(MovieDao::class.java)
    }

    @Test
    fun `insert and retrieve favorite movie`() = runBlocking {
        val movie = MovieEntity(1, "Inception", "posterPath.jpg", "03/11/2022")

        // Mock the behavior of the DAO
        whenever(dao.getAllFavorites()).thenReturn(listOf(movie))

        // Insert the movie
        dao.insertMovie(movie)

        // Retrieve the favorites
        val result = dao.getAllFavorites()

        // Assertions
        assertEquals(1, result.size)
        assertEquals("Inception", result[0].title)

        // Verify that insertMovie was called
        verify(dao).insertMovie(movie)
    }

    @Test
    fun `delete favorite movie`() = runBlocking {
        val movie = MovieEntity(1, "Inception", "posterPath.jpg", "03/11/2022")

        // Mock the behavior of the DAO
        whenever(dao.getAllFavorites()).thenReturn(listOf(movie))

        // Insert the movie
        dao.insertMovie(movie)

        // Delete the movie
        dao.deleteMovie(movie)

        // Mock the behavior after deletion
        whenever(dao.getAllFavorites()).thenReturn(emptyList())

        // Retrieve the favorites
        val result = dao.getAllFavorites()

        // Assertions
        assertTrue(result.isEmpty())

        // Verify that deleteMovie was called
        verify(dao).deleteMovie(movie)
    }
}