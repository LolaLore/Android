package com.example.lore.proiect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText username;
    EditText password;

    UserService service;

    final String server_url = "http://192.168.1.4:8080/serverMobile/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = ServiceFactory.getUserService();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);


        //animatie stuff
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.ABSOLUTE);
        anim.setDuration(700);

        // Start animating the image
        final ImageView splash = (ImageView) findViewById(R.id.imageView);
        splash.startAnimation(anim);


        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(username.getText().toString());
                System.out.println(password.getText().toString());

                String usern = username.getText().toString();
                String pass = password.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                final HashMap<String, String> user = new HashMap<String, String>();
                user.put("username", usern);
                user.put("password", pass);

                SharedPreferences sharedpreferences = getSharedPreferences("myprefs",
                        Context.MODE_PRIVATE);

                String token = sharedpreferences.getString("token",null);

                String[] items = token.split(",");
                if(usern.equals(items[0]) && pass.equals(items[1]))
                {
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Alert");
                    builder.setMessage("Wrong username or password\n Please try again and check the internet connection");
                    builder.setNegativeButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    dialog.show();
                }

                if(token.equals("")) {
                    service.doLogin(user).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                            if (response.body().getResponse().equals("true")) {
                                startActivity(new Intent(MainActivity.this, UserActivity.class));
                                SharedPreferences sharedpreferences = getSharedPreferences("myprefs",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.remove("token");
                                editor.commit();

                                editor.putString("token", user.values().toArray()[0].toString() + "," + user.values().toArray()[1].toString());
                                editor.commit();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Alert");
                                builder.setMessage("Wrong username or password\n Please try again");
                                builder.setNegativeButton("OK", null);
                                AlertDialog dialog = builder.create();
                                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                                dialog.show();

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                        }
                    });
                }
            }


        });
//
//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                System.out.println(username.getText().toString());
//                System.out.println(password.getText().toString());
//
//                String usern = username.getText().toString();
//                String pass = password.getText().toString();
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//
//                HashMap<String, String> user = new HashMap<String, String>();
//                user.put("username", usern);
//                user.put("password", pass);
//
//                JsonObjectRequest request_json = new JsonObjectRequest(
//                        server_url,
//                        new JSONObject(user),
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                System.out.println("\n");
//                                System.out.println("\n");
//                                System.out.println(response);
//                                System.out.println("\n");
//                                System.out.println("\n");
//
//                                try {
//                                    if (response.getString("response").equals("true")) {
//                                        startActivity(new Intent(MainActivity.this, UserActivity.class));
//                                    } else {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                        builder.setTitle("Alert");
//                                        builder.setMessage("Wrong username or password\n Please try again");
//                                        builder.setNegativeButton("OK", null);
//                                        AlertDialog dialog = builder.create();
//                                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
//                                        dialog.show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                VolleyLog.e("Error: ", error.getMessage());
//                            }
//                        });
//                queue.add(request_json);
//            }
//        });


    }
}
