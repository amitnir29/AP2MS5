package com.example.flightmobileapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface URLDatabaseDao {
    @Insert
    fun insert(url:myURL)

    @Query("DELETE FROM saved_urls ORDER BY urlID ASC LIMIT 1")
    fun removeOldest()

    @Query("SELECT * FROM saved_urls ORDER BY urlID ASC LIMIT 5")
    fun getRelevant() : List<myURL>
}