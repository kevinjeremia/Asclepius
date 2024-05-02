package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.ScansRepository
import com.dicoding.asclepius.data.local.room.ScansHistoryDatabase

object Injection {
    fun provideRepository(context: Context): ScansRepository {
        val database = ScansHistoryDatabase.getDatabase(context)
        val dao = database.scansDao()
        return ScansRepository.getInstance(dao)

    }
}