package com.dicoding.asclepius.view.examine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.ScansRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultViewModel(private val scansRepo: ScansRepository) : ViewModel() {

    private var size = 0

    fun getCurrentTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("EEE, MMM d yyyy 'at' K:mm a", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    fun insertScan(img: String, diagnosis: String, date: String) {
        size += 1
        viewModelScope.launch {
            scansRepo.insert(img, diagnosis, date)
        }
    }
}