package com.example.flightmobileapp;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0006\u0010\b\u001a\u00020\tH\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0003H\'J\b\u0010\f\u001a\u00020\u000bH\'J\u0010\u0010\r\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\b\u0010\u000e\u001a\u00020\tH\'\u00a8\u0006\u000f"}, d2 = {"Lcom/example/flightmobileapp/URLDatabaseDao;", "", "getByURL", "Lcom/example/flightmobileapp/myURL;", "url", "", "getRelevants", "", "amount", "", "insert", "", "removeOldest", "setLastUsed", "size", "app_debug"})
public abstract interface URLDatabaseDao {
    
    @androidx.room.Insert()
    public abstract void insert(@org.jetbrains.annotations.NotNull()
    com.example.flightmobileapp.myURL url);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "SELECT * FROM saved_urls WHERE url = :url")
    public abstract com.example.flightmobileapp.myURL getByURL(@org.jetbrains.annotations.NotNull()
    java.lang.String url);
    
    @androidx.room.Query(value = "DELETE FROM saved_urls WHERE urlID = (SELECT MIN(urlID) from saved_urls)")
    public abstract void removeOldest();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM saved_urls ORDER BY urlID DESC LIMIT :amount")
    public abstract java.util.List<com.example.flightmobileapp.myURL> getRelevants(int amount);
    
    @androidx.room.Query(value = "UPDATE saved_urls SET urlID = (SELECT MAX(urlID) FROM saved_urls) + 1 WHERE url = :url")
    public abstract void setLastUsed(@org.jetbrains.annotations.NotNull()
    java.lang.String url);
    
    @androidx.room.Query(value = "SELECT COUNT(urlID) from saved_urls")
    public abstract int size();
}