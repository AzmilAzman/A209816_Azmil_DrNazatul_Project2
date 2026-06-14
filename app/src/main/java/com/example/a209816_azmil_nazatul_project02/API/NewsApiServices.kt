package com.example.a209816_azmil_nazatul_project02.API

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "my",
        @Query("lang") lang: String = "en",
        @Query("max") max: Int = 10,
        @Query("apikey") apiKey: String   // ← fixed from "token" to "apikey"
    ): NewsResponse
}