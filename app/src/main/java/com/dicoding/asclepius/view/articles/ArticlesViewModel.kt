package com.dicoding.asclepius.view.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.response.NewsResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel : ViewModel() {

    private val _news = MutableLiveData<List<ArticlesItem>>()
    val news: LiveData<List<ArticlesItem>> = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        val client = ApiConfig.getApiService().fetchNewsFromApi()
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    _news.value = response.body()?.articles!!.filter { articlesItem ->
                        articlesItem.newsUrl != null &&
                                articlesItem.imgUrl != null &&
                                articlesItem.description != null &&
                                articlesItem.title?.contains("Ninja") != (true ?: false) &&
                                articlesItem.title != "[Removed]" &&
                                articlesItem.description != "[Removed]" &&
                                articlesItem.imgUrl != "[Removed]" &&
                                articlesItem.newsUrl != "[Removed]"
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ArticlesViewModel"
    }
}