package com.example.dogapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogApi {
        @GET("breeds")
        Call<List<Dog>> getList();
    }
