package com.example.hackprepandroidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = findViewById(R.id.submitButton);
        Spinner countrySpinner = findViewById(R.id.countrySpinner);
        Spinner citySpinner = findViewById(R.id.citySpinner);
        String array[] = {"Select", "India", "USA"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, array);

        countrySpinner.setAdapter(adapter);
        citySpinner.setAdapter(adapter);

        citySpinner.setEnabled(false);
        citySpinner.setAlpha(0.4f);

        PlacesInterface service = RetrofitClient.getRetrofitInstance().create(PlacesInterface.class);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    citySpinner.setEnabled(true);
                    citySpinner.setAlpha(1.0f);


                    Call<Places> call = service.getCities(countrySpinner.getItemAtPosition(position).toString());
                    call.enqueue(new Callback<Places>() {
                        @Override
                        public void onResponse(Call<Places> call, Response<Places> response) {
                            ArrayList<String> cities = response.body().getCities();
                            Log.e("Cities: ", cities.toString());
                            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_spinner_dropdown_item, cities);
                            citySpinner.setAdapter(cityAdapter);
                        }

                        @Override
                        public void onFailure(Call<Places> call, Throwable t) {
                            Log.e("Cities: ", t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Item: ", citySpinner.getSelectedItem().toString());
                Call<Places> call = service.getLocations(citySpinner.getSelectedItem().toString());
                call.enqueue(new Callback<Places>() {
                    @Override
                    public void onResponse(Call<Places> call, Response<Places> response) {
                        ArrayList<Places.Location> locations = response.body().getLocations();
                        String location_array[] = new String[locations.size()];
                        for(int i=0; i < locations.size(); i++) {
                            Log.e("Name:", locations.get(i).getName());
                            Log.e("Description:", locations.get(i).getDescription());
                            location_array[i] = locations.get(i).getName();
                        }
                        Intent intent = new Intent(getApplicationContext(), ReviewsActivity.class);
                        intent.putExtra("locations", location_array);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Places> call, Throwable t) {

                    }
                });
            }
        });
    }
}
