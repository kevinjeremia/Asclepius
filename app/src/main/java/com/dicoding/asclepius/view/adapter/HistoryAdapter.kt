package com.dicoding.asclepius.view.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.ScansEntity
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter(private val scansHistory: List<ScansEntity>) : ListAdapter<ScansEntity, HistoryAdapter.HistoryViewHolder>(
    DIFF_CALLBACK) {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                tvScansResultSubtitle.text = scansHistory[position].diagnosis
                tvScansResultDate.text = scansHistory[position].date
                val imgUri = Uri.parse(scansHistory[position].imageUri)
                ivResultPhoto.setImageURI(imgUri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(position)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ScansEntity> =
            object : DiffUtil.ItemCallback<ScansEntity>() {
                override fun areItemsTheSame(oldItem: ScansEntity, newItem: ScansEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ScansEntity, newItem: ScansEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}