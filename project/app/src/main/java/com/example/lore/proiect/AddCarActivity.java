package com.example.lore.proiect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class AddCarActivity extends AppCompatActivity {

    Button submitAddButton;
    private EditText brandField;
    private EditText horsePowerField;
    private EditText maxSpeedField;
    private EditText manufacturingYearField;
    private ProgressBar progressBar;
    final String server_url = "http://192.168.43.181:8080/serverMobile/addCar";

    CarService carService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        carService = ServiceFactory.getCarService();


        submitAddButton = (Button) findViewById(R.id.submitAddButton);
        brandField = (EditText) findViewById(R.id.brandField);
        horsePowerField = (EditText) findViewById(R.id.horsePowerField);
        maxSpeedField = (EditText) findViewById(R.id.maxSpeedField);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        manufacturingYearField =(EditText) findViewById(R.id.manufacturingYearField);
        progressBar.setVisibility(View.GONE);


        submitAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> car;
                car = handleAdd();
                for(Object c :car.values().toArray())
                {
                    System.out.println(c.toString());
                }
                carService.addCar(car).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                        if(response.body().getResponse().equals("true")) {
                            startActivity(new Intent(AddCarActivity.this, UserActivity.class));
                            SharedPreferences sharedpreferences = getSharedPreferences("myprefs",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("items", car.toString());
                            editor.commit();
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AddCarActivity.this);
                            builder.setTitle("Alert");
                            builder.setMessage("Something went wrong.");
                            builder.setNegativeButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                            dialog.show();
                            System.out.println("Error at request for adding the car");

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

//    private void handleAdd() {
//        String brand = brandField.getText().toString();
//        String horsePower = horsePowerField.getText().toString();
//        String maxSpeed = maxSpeedField.getText().toString();
//        String manufacturingYear = manufacturingYearField.getText().toString();
//        String errors = "";
//
//
//        if(brand.equals("")){
//            errors += "Name can't be null\n";
//        }
//        if(horsePower.equals("")){
//            errors += "Horse power can't be null\n";
//        }
//        if(maxSpeed.equals("")){
//            errors += "Max speed can't be null\n";
//        }
//        if(manufacturingYear.equals("")){
//            errors += "Manufacturing year can't be null\n";
//        }
//
//        if(!errors.equals("")){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Error");
//            builder.setMessage(errors);
//            builder.setNegativeButton("OK", null);
//            AlertDialog dialog = builder.create();
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
//            dialog.show();
//
//            return;
//        }
//
//        progressBar.setVisibility(View.VISIBLE);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                return;
//            }
//        }, 999999999);
//        RequestQueue queue = Volley.newRequestQueue(AddCarActivity.this);
//
//
//        HashMap<String, String> book = new HashMap<String, String>();
//        book.put("brand", brand);
//        book.put("horsepower", horsePower);
//        book.put("maxspeed", maxSpeed);
//        book.put("manufacturingyear",manufacturingYear);
//
//        JsonObjectRequest request_json = new JsonObjectRequest(
//                server_url,
//                new JSONObject(book),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            if (response.getString("response").equals("true")) {
//                                startActivity(new Intent(AddCarActivity.this, UserActivity.class));
//                            } else {
//                                System.out.println("Error at request for adding the car");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.e("Error: ", error.getMessage());
//                    }
//                });
//        queue.add(request_json);
//
//    }

    private HashMap<String, String> handleAdd() {
        String brand = brandField.getText().toString();
        String horsePower = horsePowerField.getText().toString();
        String maxSpeed = maxSpeedField.getText().toString();
        String manufacturingYear = manufacturingYearField.getText().toString();
        String errors = "";


        if(brand.equals("")){
            errors += "Name can't be null\n";
        }
        if(horsePower.equals("")){
            errors += "Horse power can't be null\n";
        }
        if(maxSpeed.equals("")){
            errors += "Max speed can't be null\n";
        }
        if(manufacturingYear.equals("")){
            errors += "Manufacturing year can't be null\n";
        }

        HashMap<String, String> book = new HashMap<String, String>();
        if(!errors.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage(errors);
            builder.setNegativeButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            dialog.show();

            return null;
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    return;
                }
            }, 999999999);
            book.put("brand", brand);
            book.put("horsepower", horsePower);
            book.put("maxspeed", maxSpeed);
            book.put("manufacturingyear",manufacturingYear);
            return book;
        }

    }
}
