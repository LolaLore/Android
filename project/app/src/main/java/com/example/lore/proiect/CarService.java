package com.example.lore.proiect;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CarService {

    @GET("getAll")
    Call<Page> getCars();

    @POST("deleteCar")
    @Headers("Content-Type: application/json")
    Call<LoginResponse> deleteCar(@Body HashMap<String, String> car);

    @POST("addCar")
    @Headers("Content-Type: application/json")
    Call<LoginResponse> addCar(@Body  HashMap<String, String> car);
}
