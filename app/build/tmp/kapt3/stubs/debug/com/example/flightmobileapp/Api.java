package com.example.flightmobileapp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\'J\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\'\u00a8\u0006\b"}, d2 = {"Lcom/example/flightmobileapp/Api;", "", "getImg", "Lretrofit2/Call;", "Lokhttp3/ResponseBody;", "postVals", "vals", "Lcom/example/flightmobileapp/Vals;", "app_debug"})
public abstract interface Api {
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "/screenshot")
    public abstract retrofit2.Call<okhttp3.ResponseBody> getImg();
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "/api/command")
    public abstract retrofit2.Call<okhttp3.ResponseBody> postVals(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Body()
    com.example.flightmobileapp.Vals vals);
}