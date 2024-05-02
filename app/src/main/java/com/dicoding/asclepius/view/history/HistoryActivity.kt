package com.dicoding.asclepius.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.entity.ScansEntity
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.helper.setBackNav
import com.dicoding.asclepius.helper.visible
import com.dicoding.asclepius.view.adapter.HistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    private val historyViewModel by viewModels<HistoryViewModel>{
        ViewModelFactory.getInstance(application)
    }

    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel.getScansHistory().observe(this) { scans ->
            if ((historyViewModel.size.value ?: 0) > 0) {
                setupHistoryRecyclerView(scans)
                historyAdapter.submitList(scans)
            } else {
                binding.tvHistoryEmpty.visible()
            }
        }

        binding.topBar.setBackNav()

    }

    private fun setupHistoryRecyclerView(scans: List<ScansEntity>) {
        historyAdapter = HistoryAdapter(scans)
        binding.rvScansHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            setHasFixedSize(true)
            adapter = historyAdapter
        }
    }
}