package com.example.flightmobileapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface URLDatabaseDao {
    /**
     * Insert a url to the database.
     * @param url the url object to add.
     */
    @Insert
    fun insert(url: MyURL)


    /**
     * Get a url object given its url.
     * @param url the url key.
     * @return the associated url object.
     */
    @Query("SELECT * FROM saved_urls WHERE url = :url")
    fun getByURL(url: String): MyURL?


    /**
     * Remove the oldest (in terms of last used) url.
     */
    @Query("DELETE FROM saved_urls WHERE urlID = (SELECT MIN(urlID) from saved_urls)")
    fun removeOldest()


    /**
     * Get given amount of relevant urls (newest in terms of last used) from the database.
     * @param amount the amount of urls to get.
     * @return a list of the relevant urls.
     */
    @Query("SELECT * FROM saved_urls ORDER BY urlID DESC LIMIT :amount")
    fun getRelevants(amount: Int): List<MyURL>


    /**
     * Set the url as the last used.
     * @param url the url to update.
     */
    @Query("UPDATE saved_urls SET urlID = (SELECT MAX(urlID) FROM saved_urls) + 1 " +
            "WHERE url = :url")
    fun setLastUsed(url: String)


    /**
     * Get the size (number of urls) of the database.
     */
    @Query("SELECT COUNT(urlID) from saved_urls")
    fun size(): Int
}