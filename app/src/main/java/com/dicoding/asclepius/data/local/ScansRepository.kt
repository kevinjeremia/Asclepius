package com.dicoding.asclepius.data.local

import android.net.Uri
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.entity.ScansEntity
import com.dicoding.asclepius.data.local.room.ScansDao

class ScansRepository(private val scansDao: ScansDao) {
    fun getScans(): LiveData<List<ScansEntity>> = scansDao.getScansHistory()

    fun getRecent(): LiveData<ScansEntity> = scansDao.getRecentScans()

    suspend fun size(): Int = scansDao.getDbSize()

    suspend fun insert(img: String, diagnosis: String, date: String) {
        val scansEntity = ScansEntity(
            imageUri = img,
            diagnosis = diagnosis,
            date = date)
        scansDao.insertScans(scansEntity)
    }

    companion object {
        @Volatile
        private var instance: ScansRepository? = null

        fun getInstance(
            scansDao: ScansDao
        ): ScansRepository =
            instance ?: synchronized(this) {
                instance ?: ScansRepository(scansDao)
            }.also { instance = it }
    }
}