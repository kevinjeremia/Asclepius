package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dicoding.asclepius.data.local.entity.ScansEntity

@Database(entities = [ScansEntity::class], version = 2)
abstract class ScansHistoryDatabase : RoomDatabase() {
    abstract fun scansDao(): ScansDao

    companion object {

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
        @Volatile
        private var INSTANCE: ScansHistoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ScansHistoryDatabase {
            if (INSTANCE == null) {
                synchronized(ScansHistoryDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ScansHistoryDatabase::class.java, "scanshistory_database")
                        .addMigrations(migration_1_2)
                        .build()
                }
            }
            return INSTANCE as ScansHistoryDatabase
        }
    }
}