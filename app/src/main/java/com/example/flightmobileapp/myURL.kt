package com.example.flightmobileapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_urls")
data class myURL (
        @PrimaryKey(autoGenerate = true)
        var urlID: Long = 0L,
        @ColumnInfo(name = "url")
        var url: String = ""
)