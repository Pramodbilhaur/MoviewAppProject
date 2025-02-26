package com.example.moviesappproject.repo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesappproject.model.MoiveResultList
import com.example.moviesappproject.network.ApiService
import com.example.moviesappproject.page.DiscoverMoviePageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DiscoverMoveViewModel
@Inject constructor(private val apiService: ApiService) : ViewModel()
{

    val discoverMovieResult :Flow<PagingData<MoiveResultList>> =
        Pager(config = PagingConfig(10,enablePlaceholders = false)){
        DiscoverMoviePageSource(apiService)
    }.flow.cachedIn(viewModelScope).catch {
            Log.e("TAG_DiscoverMoveViewModel", ": $it", )
        }


}