package com.dicoding.asclepius.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.ScansEntity
import com.dicoding.asclepius.databinding.ItemArticlesHeaderBinding
import com.dicoding.asclepius.databinding.ItemHomeHeaderBinding

class HeaderAdapter(private val viewHolderType: String, private val navController: NavController? = null, private val anyRecent: Boolean = false, private val scansEntity: ScansEntity? = null) : RecyclerView.Adapter<HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return when(viewHolderType) {
            HOME -> HeaderViewHolder.HomeHeaderViewHolder(
                ItemHomeHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                navController!!,
                anyRecent,
                scansEntity
            )
            ARTICLES -> HeaderViewHolder.ArticlesHeaderViewHolder(
                ItemArticlesHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        if(holder is HeaderViewHolder.HomeHeaderViewHolder) {
             holder.bind()
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    companion object {
        const val HOME = "HOME"
        const val ARTICLES = "ARTICLES"
    }
}