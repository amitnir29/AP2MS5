package com.example.flightmobileapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [myURL::class], version = 2, exportSchema = false)
abstract class SavedURLsDatabase : RoomDatabase() {
    abstract val urlDatabaseDao : URLDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SavedURLsDatabase? = null

        fun getInstance(context: Context): SavedURLsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SavedURLsDatabase::class.java,
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