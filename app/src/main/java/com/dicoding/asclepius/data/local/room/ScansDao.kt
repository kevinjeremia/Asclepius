package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.ScansEntity

@Dao
interface ScansDao {
    @Query("SELECT * FROM ScansHistory ORDER BY id DESC")
    fun getScansHistory(): LiveData<List<ScansEntity>>

    @Query("SELECT * FROM ScansHistory ORDER BY id DESC LIMIT 1")
    fun getRecentScans(): LiveData<ScansEntity>

    @Query("SELECT COUNT(*) FROM ScansHistory")
    suspend fun getDbSize(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScans(scans: ScansEntity)
}