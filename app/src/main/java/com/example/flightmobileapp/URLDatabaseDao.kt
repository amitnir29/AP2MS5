package com.example.flightmobileapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface URLDatabaseDao {
    @Insert
    fun insert(url:myURL)

    @Query("SELECT * FROM saved_urls WHERE url = :url")
    fun getByURL(url: String): myURL?


    @Query("DELETE FROM saved_urls WHERE urlID = (SELECT MIN(urlID) from saved_urls)")
    fun removeOldest()

    @Query("SELECT * FROM saved_urls ORDER BY urlID DESC LIMIT :amount")
    fun getRelevants(amount: Int): List<myURL>

    @Query("UPDATE saved_urls SET urlID = (SELECT MAX(urlID) FROM saved_urls) + 1 " +
            "WHERE url = :url")
    fun setLastUsed(url: String)

    @Query("SELECT COUNT(urlID) from saved_urls")
    fun size(): Int
}