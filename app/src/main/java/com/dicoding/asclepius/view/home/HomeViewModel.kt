package com.dicoding.asclepius.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.ScansRepository
import com.dicoding.asclepius.data.local.entity.ScansEntity
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.response.NewsResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val scansRepo: ScansRepository) : ViewModel() {

    private val _news = MutableLiveData<List<ArticlesItem>>()
    val news: LiveData<List<ArticlesItem>> = _news

    private var _size = MutableLiveData(0)
    val size: LiveData<Int> = _size

    init {
        fetchNews()
        getRecentScans()
    }

    private fun getDbSize() {
        viewModelScope.launch {
            _size.value = scansRepo.size()
        }
    }

    fun getRecentScans(): LiveData<ScansEntity> {
        getDbSize()
        return scansRepo.getRecent()
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
        private const val TAG = "HomeViewModel"
    }
}