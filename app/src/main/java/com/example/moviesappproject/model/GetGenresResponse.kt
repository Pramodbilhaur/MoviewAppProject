package com.example.moviesappproject.model


import com.example.moviesappproject.model.GenreResultList
import com.google.gson.annotations.SerializedName

data class GetGenresResponse(
    @SerializedName("genres")
    val genres: List<GenreResultList>
)