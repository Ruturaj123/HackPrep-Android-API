package com.example.hackprepandroidapi;

import com.example.hackprepandroidapi.model.Places;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PlacesInterface {

    @GET("/cities")
    Call<Places> getCities(@Query("country") String country);

    @GET("/locations")
    Call<Places> getLocations(@Query("city") String city);

    @GET("/reviews")
    Call<Places> getReviews(@Query("location") String location);

    @POST("/reviews")
    Call<Places> getSentimentScore(@Body Places place);

}
