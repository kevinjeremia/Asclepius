package com.dicoding.asclepius.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.ScansRepository
import com.dicoding.asclepius.data.local.entity.ScansEntity
import kotlinx.coroutines.launch

class HistoryViewModel(private val scansRepo: ScansRepository) : ViewModel() {

    private var _size = MutableLiveData(0)
    val size: LiveData<Int> = _size

    init {
//        getScansHistory()
    }

    private fun getDbSize() {
        viewModelScope.launch {
            _size.value = scansRepo.size()
        }
    }

    fun getScansHistory(): LiveData<List<ScansEntity>> {
        getDbSize()
        return scansRepo.getScans()
    }
}