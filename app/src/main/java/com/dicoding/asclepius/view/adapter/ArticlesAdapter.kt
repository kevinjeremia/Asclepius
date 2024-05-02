package com.dicoding.asclepius.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ItemArticlesBinding
import com.dicoding.asclepius.helper.loadImage

class ArticlesAdapter(private val tabType: String, val newsList: List<ArticlesItem>? = null) : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {
    inner class ArticlesViewHolder(private val binding: ItemArticlesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (newsList != null) {
                binding.apply{
                    cvArticles.setOnClickListener {
                        val webpage: Uri = Uri.parse(newsList[position].newsUrl)
                        val intent = Intent(Intent.ACTION_VIEW, webpage)
                        cvArticles.context.startActivity(intent)
                    }

                    articlesThumbnail.loadImage(newsList[position].imgUrl!!)
                    tvArticleTitle.text = newsList[position].title!!.trim()
                    tvArticleSubtitle.text = newsList[position].description!!.trim()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            ItemArticlesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return when(tabType) {
            HOME -> 6
            ARTICLES -> newsList?.size ?: 0
            else -> 0
        }
    }

    companion object {
        const val HOME = "HOME"
        const val ARTICLES = "ARTICLES"
    }
}