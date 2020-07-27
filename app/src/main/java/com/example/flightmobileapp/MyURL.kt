package com.example.flightmobileapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A data class used to save urls in the database.
 */
@Entity(tableName = "saved_urls", indices = [Index(value = ["url"], unique = true)])
data class MyURL (
        // An incrementing id for the urls.
        @PrimaryKey(autoGenerate = true)
        var urlID: Long = 0L,

        // The url.
        @ColumnInfo(name = "url")
        var url: String = ""
)