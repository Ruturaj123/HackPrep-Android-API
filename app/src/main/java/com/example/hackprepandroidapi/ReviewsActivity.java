package com.example.hackprepandroidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hackprepandroidapi.model.Places;
import com.example.hackprepandroidapi.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsActivity extends AppCompatActivity {
    ArrayList<String> reviews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        String[] locations = getIntent().getStringArrayExtra("locations");
        Spinner locationSpinner = findViewById(R.id.locationSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, locations);

        locationSpinner.setAdapter(adapter);

        PlacesInterface service = RetrofitClient.getRetrofitInstance().create(PlacesInterface.class);

        Button submit = findViewById(R.id.submit);
        Button scoreButton = findViewById(R.id.scoreButton);

        scoreButton.setEnabled(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Location:::::", locationSpinner.getSelectedItem().toString());
                Call<Places> call = service.getReviews(locationSpinner.getSelectedItem().toString());
                call.enqueue(new Callback<Places>() {
                    @Override
                    public void onResponse(Call<Places> call, Response<Places> response) {
                        Log.e("Reviews: ", response.body().getReviews().toString());
                        reviews =  response.body().getReviews();
                        scoreButton.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<Places> call, Throwable t) {
                        Log.e("Fail:", t.getMessage());
                    }
                });
            }
        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Places place = new Places(null, null, reviews, 0);
                Call<Places> call = service.getSentimentScore(place);
                call.enqueue(new Callback<Places>() {
                    @Override
                    public void onResponse(Call<Places> call, Response<Places> response) {
                        Log.e("Score: ", String.valueOf(response.body().getSentiment_score()));
                        scoreButton.setText(String.valueOf(response.body().getSentiment_score()));
                    }

                    @Override
                    public void onFailure(Call<Places> call, Throwable t) {
                        Log.e("Fail:", t.getMessage());
                    }
                });
            }
        });


    }
}
