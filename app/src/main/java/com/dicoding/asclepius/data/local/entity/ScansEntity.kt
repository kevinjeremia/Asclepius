package com.dicoding.asclepius.data.local.entity

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ScansHistory")
data class ScansEntity(
    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageUri: String,
    val diagnosis: String,
    val date: String
)
