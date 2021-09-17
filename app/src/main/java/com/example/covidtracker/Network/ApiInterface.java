package com.example.covidtracker.Network;

import com.example.covidtracker.Model.GlobalResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("all")
    Call<GlobalResponse> globalResponse();
}
