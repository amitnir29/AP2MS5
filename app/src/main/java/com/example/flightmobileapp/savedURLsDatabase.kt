package com.example.flightmobileapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [myURL::class], version = 1, exportSchema = false)
abstract class savedURLsDatabase : RoomDatabase() {
    abstract val urlDatabaseDao : URLDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: savedURLsDatabase? = null

        fun getInstance(context: Context): savedURLsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            savedURLsDatabase::class.java,
                            "urls_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}