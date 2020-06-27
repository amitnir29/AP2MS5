package com.example.flightmobileapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [myURL::class], version = 2, exportSchema = false)
abstract class SavedURLsDatabase : RoomDatabase() {
    abstract val urlDatabaseDao : URLDatabaseDao

    val capacity: Int
        get() = 5

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

    fun uriConnected(uri: String) {
        if (this.urlDatabaseDao.getByURL(uri) == null) {
            // Save the uri to the database for future usage.
            val url = myURL(url = uri)
            urlDatabaseDao.insert(url)

            /* If database already contains more than the required elements,
             * remove the oldest*/
            val databaseSize = this.urlDatabaseDao.size()
            if (databaseSize == this.capacity)
                this.urlDatabaseDao.removeOldest()
        }
        else {
            this.urlDatabaseDao.setLastUsed(uri)
        }
    }


    fun getRelevants(): List<String> {
        val urlObjs = urlDatabaseDao.getRelevants(capacity)

        var urls: MutableList<String> = mutableListOf()

        for (urlObj: myURL in urlObjs)
            urls.add(urlObj.url)

        return urls
    }
}