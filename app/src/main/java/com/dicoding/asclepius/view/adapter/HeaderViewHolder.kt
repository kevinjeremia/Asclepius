package com.dicoding.asclepius.view.adapter

import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.ScansEntity
import com.dicoding.asclepius.databinding.ItemArticlesHeaderBinding
import com.dicoding.asclepius.databinding.ItemHomeHeaderBinding
import com.dicoding.asclepius.helper.gone
import com.dicoding.asclepius.helper.visible
import com.dicoding.asclepius.view.history.HistoryActivity
import java.util.Calendar

sealed class HeaderViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class HomeHeaderViewHolder(private val binding: ItemHomeHeaderBinding, private val navController: NavController, private val anyRecent: Boolean, private val scansEntity: ScansEntity? = null) : HeaderViewHolder(binding) {
        fun bind() {
            binding.apply {
                val currentTime = getCurrentTime()
                tvGreetings.text = getGreetings(currentTime)

                if (anyRecent) {
                    tvScansResultSubtitle.text = scansEntity!!.diagnosis
                    tvScansResultDate.text = scansEntity.date
                    val recentUri = Uri.parse(scansEntity.imageUri)
                    ivResultPhoto.setImageURI(recentUri)

                    tvAllResults.visible()
                    tvRecentScans.visible()
                    cvRecentScans.visible()
                } else {
                    tvAllResults.gone()
                    tvRecentScans.gone()
                    cvRecentScans.gone()
                }

                btnExamine.setOnClickListener {
                    navController.popBackStack(R.id.homeFragment, true)
                    navController.navigate(R.id.examineFragment)
                }

                tvAllResults.setOnClickListener {
                    val intent = Intent(tvAllResults.context, HistoryActivity::class.java)
                    tvAllResults.context.startActivity(intent)
                }

                tvMoreArticles.setOnClickListener {
                    navController.popBackStack(R.id.homeFragment, true)
                    navController.navigate(R.id.articlesFragment)
                }

            }
        }

        private fun getCurrentTime(): Int {
            return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        }

        private fun getGreetings(currentTime: Int): String {
            return when(currentTime) {
                in 5..11 -> "Good morning!"
                in 12..17 -> "Good afternoon!"
                in 18..21 -> "Good evening!"
                else -> "Good night!"
            }
        }

    }

    class ArticlesHeaderViewHolder(private val binding: ItemArticlesHeaderBinding) : HeaderViewHolder(binding) {    }


}