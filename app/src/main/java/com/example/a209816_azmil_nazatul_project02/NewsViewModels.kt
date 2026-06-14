package com.example.a209816_azmil_nazatul_project02

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a209816_azmil_nazatul_project02.API.Article
import com.example.a209816_azmil_nazatul_project02.API.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

class NewsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    // Replace with your actual GNews API key
    private val API_KEY = "6c8119404f5a1a7deced54ca692a98f1"

    init {
        fetchNews()
    }

    fun fetchNews() {
        _uiState.value = NewsUiState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitClient.newsApiService.getTopHeadlines(
                    country = "my",
                    lang = "en",
                    apiKey = API_KEY
                )
                _uiState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}