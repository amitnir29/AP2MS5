package com.example.flightmobileapp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\'J\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\'\u00a8\u0006\t"}, d2 = {"Lcom/example/flightmobileapp/Api;", "", "getImg", "Lkotlinx/coroutines/Deferred;", "Lokhttp3/ResponseBody;", "postVals", "vals", "Lcom/example/flightmobileapp/Vals;", "Companion", "app_debug"})
public abstract interface Api {
    public static final com.example.flightmobileapp.Api.Companion Companion = null;
    
    /**
     * A get function.
     * Ask the server for an image.
     */
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "/screenshot")
    public abstract kotlinx.coroutines.Deferred<okhttp3.ResponseBody> getImg();
    
    /**
     * A post function.
     * Post values to the command section of the server.
     * @param vals the values to send in the body.
     */
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "/api/command")
    public abstract kotlinx.coroutines.Deferred<okhttp3.ResponseBody> postVals(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.example.flightmobileapp.Vals vals);
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2 = {"Lcom/example/flightmobileapp/Api$Companion;", "", "()V", "api", "Lcom/example/flightmobileapp/Api;", "getApi", "()Lcom/example/flightmobileapp/Api;", "setApi", "(Lcom/example/flightmobileapp/Api;)V", "init", "", "url", "", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        public static com.example.flightmobileapp.Api api;
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.flightmobileapp.Api getApi() {
            return null;
        }
        
        public final void setApi(@org.jetbrains.annotations.NotNull()
        com.example.flightmobileapp.Api p0) {
        }
        
        /**
         * Initialize the api.
         * @prama url the url to connect the api to.
         */
        public final void init(@org.jetbrains.annotations.NotNull()
        java.lang.String url) {
        }
        
        private Companion() {
            super();
        }
    }
}