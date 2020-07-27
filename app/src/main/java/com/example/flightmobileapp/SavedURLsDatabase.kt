package com.example.flightmobileapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * A database class for the urls database.
 * Get an instance via [getInstance] static method.
 */
@Database(entities = [MyURL::class], version = 2, exportSchema = false)
abstract class SavedURLsDatabase : RoomDatabase() {
    // A reference to the dao object.
    abstract val urlDatabaseDao : URLDatabaseDao

    // Property for capacity.
    private val capacity: Int
        get() = 5

    companion object {
        // A singleton object of the class.
        @Volatile
        private var INSTANCE: SavedURLsDatabase? = null


        /**
         * Get an instance (singleton) of the class.
         * @param context the context.
         */
        fun getInstance(context: Context): SavedURLsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                // If haven't created an instance yet create one.
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
                // Return the singleton instance.
                return instance
            }
        }
    }


    /**
     * Add a uri to the database.
     * @param uri the uri to add.
     */
    fun uriConnected(uri: String) {
        // If haven'tsaved the uri yet:
        if (this.urlDatabaseDao.getByURL(uri) == null) {
            // Save the uri to the database for future usage.
            val url = MyURL(url = uri)
            urlDatabaseDao.insert(url)

            /* If database already contains more than the required elements,
             * remove the oldest*/
            val databaseSize = this.urlDatabaseDao.size()
            if (databaseSize == this.capacity)
                this.urlDatabaseDao.removeOldest()
        }
        else {
            // If the uri exists, update its usage time.
            this.urlDatabaseDao.setLastUsed(uri)
        }
    }


    fun getRelevants(): List<String> {
        val urlObjs = urlDatabaseDao.getRelevants(capacity)

        var urls: MutableList<String> = mutableListOf()

        for (urlObj: MyURL in urlObjs)
            urls.add(urlObj.url)

        return urls
    }
}