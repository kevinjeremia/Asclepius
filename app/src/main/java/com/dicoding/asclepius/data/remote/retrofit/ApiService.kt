package com.dicoding.asclepius.data.remote.retrofit


import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("v2/everything")
    fun fetchNewsFromApi(
        @Query("q") theme: String = "skin cancer",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Call<NewsResponse>
}