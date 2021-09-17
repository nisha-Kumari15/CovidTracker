package com.example.covidtracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.covidtracker.Model.GlobalResponse;
import com.example.covidtracker.Network.ApiClient;
import com.example.covidtracker.Network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Stats extends AppCompatActivity {

    TextView cases , todayCases , deaths , todayDeaths , recovered , active ,critical , affectedCountries;
    Button button;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        cases = findViewById(R.id.cases);
        todayCases = findViewById(R.id.todayCases);
        deaths = findViewById(R.id.deaths);
        todayDeaths = findViewById(R.id.todayDeaths);
        recovered = findViewById(R.id.recovered);
        active = findViewById(R.id.active);
        critical = findViewById(R.id.critical);
        affectedCountries = findViewById(R.id.affectedCountries);
        button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Stats.this , AllCountries.class));
            }
        });


        apicall();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apicall() {

        ApiInterface apiInterface = null;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<GlobalResponse> call = apiInterface.globalResponse();

        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                if(response.isSuccessful())
                {
                    String Scase = String.valueOf(response.body().getCases());
                    String StodayCase = String.valueOf(response.body().getTodayCases());
                    String stDeaths = String.valueOf(response.body().getDeaths());
                    String stTodaysDeath = String.valueOf(response.body().getTodayDeaths());
                    String stRecocered = String.valueOf(response.body().getRecovered());
                    String stActive = String.valueOf(response.body().getActive());
                    String stCritical = String.valueOf(response.body().getCritical());
                    String stCountires = String.valueOf(response.body().getAffectedCountries());

                    cases.setText(Scase);
                    todayCases.setText(StodayCase);
                    deaths.setText(stDeaths);
                    todayDeaths.setText(stTodaysDeath);
                    recovered.setText(stRecocered);
                    active.setText(stActive);
                    critical.setText(stCritical);
                    affectedCountries.setText(stCountires);

                }
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {

            }
        });
    }


}
