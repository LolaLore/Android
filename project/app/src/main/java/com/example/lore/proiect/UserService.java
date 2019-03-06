package com.example.lore.proiect;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @POST("login")
    @Headers("Content-Type: application/json")
    Call<LoginResponse> doLogin(@Body HashMap<String, String> user);
}
