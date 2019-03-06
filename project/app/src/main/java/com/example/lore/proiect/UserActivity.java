package com.example.lore.proiect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.transform.Source;

import retrofit2.Call;
import retrofit2.Callback;

public class UserActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private ListView listView;
    Button logoutButton;
    Button addButton;
    Button deleteButton;
    Button chartButton;
    Button showButton;
    Button emailButton;
    final String server_url_getAll = "http://192.168.43.181:8080/serverMobile/getAll";
    final String server_url_delete = "http://192.168.43.181:8080/serverMobile/deleteCar";
    private ArrayList<Car> cars = new ArrayList<>();
    private List<String> carsString = new ArrayList<>();
    private String selectItem = "";
    private ArrayAdapter<Car> arrayAdapter;

    //RadioButton carElem;
    public static List<Car> allcars = new ArrayList<>();

    CarService carService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        loadFromlocalStorage();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CarListFragment.newInstance())
                    .commitNow();
        }

        carService = ServiceFactory.getCarService();

        try {
            frameLayout = findViewById(R.id.container);
            frameLayout.setVisibility(View.GONE);

            logoutButton = (Button) findViewById(R.id.logoutButton);
            chartButton = (Button) findViewById(R.id.chartButton);
            addButton = (Button) findViewById(R.id.addButton);
            deleteButton = (Button) findViewById(R.id.deleteButton);
            showButton = (Button) findViewById(R.id.showButton);
            emailButton = findViewById(R.id.emailButton);

            this.emailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String to = "lorelove53@gmail.com";
                    String subject = "Mail from app";
                    String text = "Mail send from my app";
                    intent.setData(Uri.parse("mailto:"));
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL,to);
                    intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                    intent.putExtra(Intent.EXTRA_TEXT,text);
                    try{
                        startActivity(Intent.createChooser(intent,"How to send email?"));
                    }catch (android.content.ActivityNotFoundException ex)
                    {
                        ex.getStackTrace();
                    }
                }
            });

            this.logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserActivity.this, MainActivity.class));

                }
            });

            this.chartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserActivity.this, ChartActivity.class));
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserActivity.this, AddCarActivity.class));
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final HashMap<String, String> car;
                    car = handleDelete();
                    carService.deleteCar(car).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                            if (response.body().getResponse().equals("true")) {
                                SharedPreferences sharedpreferences = getSharedPreferences("myprefs",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.remove("items");
                                editor.commit();
                                editor.putString("items", cars.toString());
                                editor.commit();
                            } else {
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserActivity.this);
                                builder.setTitle("Alert");
                                builder.setMessage("Something went wrong.");
                                builder.setNegativeButton("OK", null);
                                android.support.v7.app.AlertDialog dialog = builder.create();
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
            showButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleShowHide();
                }
            });

            //getAllCar();
//            this.arrayAdapter = new ArrayAdapter<Car>(
//                    this,
//                    android.R.layout.simple_list_item_single_choice,
//                    cars);
//            myListView.setAdapter(arrayAdapter);


        } catch (Exception e) {
            System.out.print(e);

        }


    }

//    private void getAllCar() {
//        System.out.println("AM INTRAT IN GETTTTTTTT");
//
//        RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
//
//        System.out.println("AM TRECUT DE QUEQQQQQQQQQ");
//        final JsonObjectRequest request_json = new JsonObjectRequest(
//                Request.Method.GET,
//                server_url_getAll,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            if (response != null) {
//                                cars.clear();
//                                Integer i = 0;
//                                while (i < response.length()) {
//                                    JSONObject object = response.getJSONObject(String.valueOf(i));
//                                    Car b = new Car();
//                                    b.setId(Integer.valueOf(object.getString("id")));
//                                    b.setBrand(object.getString("brand"));
//                                    b.setHorsePower(object.getString("horsePower"));
//                                    b.setMaxSpeed(Double.valueOf(object.getString("maxSpeed")));
//                                    b.setManufacturingYear(Integer.valueOf(object.getString("manufacturingYear")));
//                                    cars.add(b);
//                                    carsString.add(object.getString("brand"));
//                                    i++;
//
//                                }
//                            } else {
//                                System.out.println("========= Eror at request for listview items ============");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
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
//    }

    private void handleShowHide() {
        if (showButton.getText().equals("Show")) {
            showButton.setText("Hide");
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            showButton.setText("Show");
            frameLayout.setVisibility(View.GONE);
        }
    }

//    private void handleDelete() {
//        int p = 0;
//        if (p != ListView.INVALID_POSITION) {
//            final String  s = selectItem;
//            RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
//            HashMap<String, String> ballet = new HashMap<String, String>();
//            ballet.put("brand", s);
//            JsonObjectRequest request_json = new JsonObjectRequest(
//                    server_url_delete,
//                    new JSONObject(ballet),
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                if (response.getString("response").equals("true")) {
//                                    setCars(s);
//
//                                } else {
//                                    System.out.println("Error at request for deleting the book");
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            VolleyLog.e("Error: ", error);
//                        }
//                    });
//            queue.add(request_json);
//
//        } else {
//            AlertDialog alertDialog = new AlertDialog.Builder(UserActivity.this).create();
//            alertDialog.setTitle("Alert");
//            alertDialog.setMessage("Please select one item from the table");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
//        }
//    }

    private HashMap<String, String> handleDelete() {
        //String p = carElem.getText().toString();
        //System.out.println(p);
        HashMap<String, String> car = new HashMap<String, String>();
        boolean selected = false;
        SharedPreferences sharedpreferences = getSharedPreferences("myprefs",
                Context.MODE_PRIVATE);

        String allP = sharedpreferences.getString("items", null);
        String allPP = allP.substring(1, allP.length() - 1);

        String[] all = allPP.split(";");
        for (String s : all) {
            String[] item = s.split(",");
            if (!item[0].equals("")) {
                Car c = new Car(Integer.parseInt(item[0]), item[1], item[2], Double.parseDouble(item[3]), Integer.parseInt(item[4]), Boolean.parseBoolean(item[5]));
                cars.add(c);
            } else {
                String e = item[1].substring(1);
                Car c = new Car(Integer.parseInt(e), item[2], item[3], Double.parseDouble(item[4]), Integer.parseInt(item[5]), Boolean.parseBoolean(item[6]));
                cars.add(c);
            }
        }
        System.out.println("IN DELETE " + cars.size());

        for (Car c : cars) {
            System.out.println(c.toString());
            if (c.getChecked()) {
                car.put("brand", c.getBrand());
                selected = true;
            }
        }

        if (selected) {
            return car;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(UserActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Please select one item from the table");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return null;
        }
    }

    private void loadFromlocalStorage()
    {
        SharedPreferences sharedpreferences = getSharedPreferences("myprefs",
            Context.MODE_PRIVATE);

        String allP = sharedpreferences.getString("items", null);
        String allPP = allP.substring(1, allP.length() - 1);

        String[] all = allPP.split(";");
        for (String s : all) {
            String[] item = s.split(",");
            if (!item[0].equals("")) {
                Car c = new Car(Integer.parseInt(item[0]), item[1], item[2], Double.parseDouble(item[3]), Integer.parseInt(item[4]), Boolean.parseBoolean(item[5]));
                allcars.add(c);
            } else {
                String e = item[1].substring(1);
                Car c = new Car(Integer.parseInt(e), item[2], item[3], Double.parseDouble(item[4]), Integer.parseInt(item[5]), Boolean.parseBoolean(item[6]));
                allcars.add(c);
            }
        }

//        this.arrayAdapter = new ArrayAdapter<Car>(
//                    this,
//                    android.R.layout.simple_list_item_single_choice,
//                    cars);
//        listView.setAdapter(arrayAdapter);

    }

//    private void setCars(String s) {
//        carsString.clear();
//
//        //getAllCar();
//        Car ballet_to_remove = null;
//        System.out.println(" s = " + s + " - Cars === "  + cars.toString());
//        for (Car ballet : this.cars) {
//            if(ballet.getBrand().equals(s)){
//                ballet_to_remove = ballet;
//            }
//        }
//        arrayAdapter.remove(ballet_to_remove);
//        arrayAdapter.notifyDataSetChanged();
//    }

}
