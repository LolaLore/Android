package com.example.lore.proiect;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Student on 10.12.2018.
 */

public class ServiceFactory {

    //static String BASE_URL = "http://192.168.1.4:8080/serverMobile/";
    static String BASE_URL = "http://192.168.43.181:8080/serverMobile/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static UserService getUserService() {
        return retrofit.create(UserService.class);
    }

    public static CarService getCarService() { return retrofit.create(CarService.class);}

}
