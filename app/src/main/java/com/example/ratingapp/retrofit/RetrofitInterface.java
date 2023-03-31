package com.example.ratingapp.retrofit;

import com.example.ratingapp.model.Content;
import com.example.ratingapp.model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    RetrofitInterface retrofitInterface = new Retrofit.Builder()
            .baseUrl("http://192.168.100.14:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface.class);

    @GET("/api/content")
    Call<List<Content>> getAllContent();

    @POST("/api/content")
    Call<Content> addNewContent(@Body HashMap<String, String> newContent);

    @POST("/api/user")
    Call<User> registerUser(@Body HashMap<String, String> registerUser);
}
