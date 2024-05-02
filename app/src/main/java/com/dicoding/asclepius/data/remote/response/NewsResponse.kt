package com.dicoding.asclepius.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>
)

@Parcelize
data class ArticlesItem(

	@field:SerializedName("urlToImage")
	val imgUrl: String?,

	@field:SerializedName("description")
	val description: String?,

	@field:SerializedName("title")
	val title: String?,

	@field:SerializedName("url")
	val newsUrl: String?
) : Parcelable
