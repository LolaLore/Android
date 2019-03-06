package com.example.lore.proiect;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarViewModel extends ViewModel {

    private static final String TAG = CarViewModel.class.getCanonicalName();
    private MutableLiveData<List<Car>> cars;

    public LiveData<List<Car>> getCars() {
        if (cars == null) {
            cars = new MutableLiveData<List<Car>>();
            loadCars();
        }
        return cars;
    }


    CarService carService;

    private void loadCars() {
        carService = ServiceFactory.getCarService();


        //NEED PAGE BECAUSE RESPONSE IS OBJECT, RESPONSE CANNOT BE LIST
        Call<Page> call = carService.getCars();
        Log.d(TAG, "loadCars");
        call.enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                Log.d(TAG, "loadCars succeeded");
                cars.setValue(response.body().getCars());
                for(Car c:cars.getValue())
                    c.setChecked(false);

            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.e(TAG, "loadCars failed ", t);
                cars.setValue(UserActivity.allcars);
            }
        });

    }
}
