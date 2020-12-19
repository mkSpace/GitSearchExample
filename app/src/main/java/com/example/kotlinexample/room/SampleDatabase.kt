package com.example.kotlinexample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kotlinexample.BuildConfig
import com.example.kotlinexample.search.Repository
import com.example.kotlinexample.search.RepositoryDao

@Database(
    entities = [Repository::class],
    version = 1
)
abstract class SampleDatabase : RoomDatabase() {

    companion object {
        private val IS_CLEAR_ALL = java.lang.Boolean.parseBoolean("false")

        private const val DB_NAME = "grand.db"

        @Volatile
        private var INSTANCE: SampleDatabase? = null

        fun getInstance(context: Context): SampleDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): SampleDatabase =
            Room.databaseBuilder(context, SampleDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .apply {
                    if (BuildConfig.DEBUG && IS_CLEAR_ALL) {
                        addCallback(CALLBACK_CLEAR_ALL)
                    }
                }
                .build()

        private val CALLBACK_CLEAR_ALL = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

            }
        }
    }

    abstract fun repositoryDao(): RepositoryDao
}