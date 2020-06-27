package com.example.flightmobileapp;

import java.lang.System;

@androidx.room.Database(entities = {com.example.flightmobileapp.myURL.class}, version = 2, exportSchema = false)
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\'\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rR\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0012"}, d2 = {"Lcom/example/flightmobileapp/SavedURLsDatabase;", "Landroidx/room/RoomDatabase;", "()V", "capacity", "", "getCapacity", "()I", "urlDatabaseDao", "Lcom/example/flightmobileapp/URLDatabaseDao;", "getUrlDatabaseDao", "()Lcom/example/flightmobileapp/URLDatabaseDao;", "getRelevants", "", "", "uriConnected", "", "uri", "Companion", "app_debug"})
public abstract class SavedURLsDatabase extends androidx.room.RoomDatabase {
    private static volatile com.example.flightmobileapp.SavedURLsDatabase INSTANCE;
    public static final com.example.flightmobileapp.SavedURLsDatabase.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.flightmobileapp.URLDatabaseDao getUrlDatabaseDao();
    
    public final int getCapacity() {
        return 0;
    }
    
    public final void uriConnected(@org.jetbrains.annotations.NotNull()
    java.lang.String uri) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRelevants() {
        return null;
    }
    
    public SavedURLsDatabase() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/example/flightmobileapp/SavedURLsDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/example/flightmobileapp/SavedURLsDatabase;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.flightmobileapp.SavedURLsDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}