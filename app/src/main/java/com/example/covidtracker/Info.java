package com.example.covidtracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Info extends AppCompatActivity {

    TextView cases , todayCases , deaths , todayDeaths , recovered , active ,critical ,  currentStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        cases = findViewById(R.id.cases);
        todayCases = findViewById(R.id.todayCases);
        deaths = findViewById(R.id.deaths);
        todayDeaths = findViewById(R.id.todayDeaths);
        recovered = findViewById(R.id.recovered);
        critical = findViewById(R.id.critical);
        active = findViewById(R.id.active);
        currentStats = findViewById(R.id.currentStats);

        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(countryName);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        currentStats.setText(countryName+" Stats");

        ApiCall(countryName);
    }

    private void ApiCall(String countryName) {

        String URL = "https://corona.lmao.ninja/v2/countries/"+countryName;

        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            cases.setText(jsonObject.getString("cases"));
                            todayCases.setText(jsonObject.getString("todayCases"));
                            todayDeaths.setText(jsonObject.getString("todayDeaths"));
                            deaths.setText(jsonObject.getString("deaths"));
                            recovered.setText(jsonObject.getString("recovered"));
                            active.setText(jsonObject.getString("active"));
                            critical.setText(jsonObject.getString("critical"));


                            Log.d("cases" , jsonObject.getString("cases"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Info.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue =  Volley.newRequestQueue(this);


        requestQueue.add(request);

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Info.this , AllCountries.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }


}
