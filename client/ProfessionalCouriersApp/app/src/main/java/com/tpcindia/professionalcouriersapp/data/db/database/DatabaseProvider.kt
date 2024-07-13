package com.tpcindia.professionalcouriersapp.data.db.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    // Singleton prevents multiple instances of database opening at the
    // same time.
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .build()
            INSTANCE = instance
            return instance
        }
    }
}