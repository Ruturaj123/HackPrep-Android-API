package com.example.hackprepandroidapi.model;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Places {

    public class Location {
        @SerializedName("name")
        String name;

        @SerializedName("description")
        String description;

        public Location(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    @SerializedName("cities")
    private ArrayList<String> cities;

    @SerializedName("locations")
    private ArrayList<Location> locations;

    @SerializedName("reviews")
    private ArrayList<String> reviews;

    @SerializedName("sentiment_score")
    private float sentiment_score;

    public Places(ArrayList<String> cities, ArrayList<Location> locations, ArrayList<String> reviews,
                  float sentiment_score) {
        this.cities = cities;
        this.locations = locations;
        this.reviews = reviews;
        this.sentiment_score = sentiment_score;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public float getSentiment_score() {
        return sentiment_score;
    }

    //    @SerializedName("city")
//    private String city;
//
//    @SerializedName("location")
//    private String location;
//
//    @SerializedName("review")
//    private String review;

//    public Places(ArrayList<String> country, String city, String location, String review) {
//        this.country = country;
//        this.city = city;
//        this.location = location;
//        this.review = review;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getReview() {
//        return review;
//    }
//
//    public void setReview(String review) {
//        this.review = review;
//    }
}
