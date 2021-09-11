package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    EditText editText;
    Button button;
    double ab, abc;
    LocationManager locationManager;
    RelativeLayout  rv,rvhava;
    TextView textView, condition, sehirad, hissedilen, nem, wind, basınc, bulut, uv, hava, yarın, yarın2, yarın3, yarın4, yarın5, yarın6;
    ImageView imageView, ıcon, bugunıcon, yarınıcon, yarınıcon2,ekle;
    private WeatherAdapter weatherAdapter;
    private RecyclerView recyclerView;
    private ArrayList<WeatherModel> weatherModelArrayList;
    FusedLocationProviderClient fusedLocationProviderClient;
    //String  address;
    String city,havaa2;
    String fnialAddress;
    double latidu, longtide;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText = findViewById(R.id.edit);
        textView = findViewById(R.id.temparatureee);
        hissedilen = findViewById(R.id.hissedilen1);
        nem = findViewById(R.id.nem1);
        hava=findViewById(R.id.kalite1);
        rv=findViewById(R.id.asılrv);
        yarın = findViewById(R.id.yarın1);
        button = findViewById(R.id.button);
        yarın2 = findViewById(R.id.yarın2);

        yarın3 = findViewById(R.id.yarın3);
        yarın4 = findViewById(R.id.yarın4);
        yarın5 = findViewById(R.id.yarın5);
        yarın6 = findViewById(R.id.yarın6);
        wind = findViewById(R.id.wind1);
        rvhava=findViewById(R.id.rvhava);
        bugunıcon = findViewById(R.id.bugunıcon);
        yarınıcon = findViewById(R.id.yarınıcon);
        yarınıcon2 = findViewById(R.id.yarınıcon2);
        basınc = findViewById(R.id.basınc1);
        bulut = findViewById(R.id.bulut1);
        uv = findViewById(R.id.uv1);
        ıcon = findViewById(R.id.IVıcon);
        sehirad = findViewById(R.id.sehiradı);
        condition = findViewById(R.id.tvcondition);
        recyclerView = findViewById(R.id.rvweather);
        weatherModelArrayList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherModelArrayList);
        recyclerView.setAdapter(weatherAdapter);
        imageView = findViewById(R.id.serachh);
        city = editText.getText().toString();
        String ad = textView.getText().toString();
        rv.setVisibility(View.GONE);




     rvhava.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             fnialAddress=hava.getText().toString();
             Intent intent=new Intent(getApplicationContext(),hava.class);
             intent.putExtra("hava",fnialAddress);
             startActivity(intent);
         }
     });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //asd();
                if (ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location locationn=task.getResult();

                            if (locationn != null){

                                try {
                                    Geocoder geocoder=new Geocoder(MainActivity2.this, Locale.getDefault());
                                    List<Address> addresses=geocoder.getFromLocation(locationn.getLatitude(),locationn.getLongitude(),1);




                                    String address = addresses.get(0).getAdminArea();
                                    //sehirad.setText(address);
                                    editText.setText(address);
                                    getWeather(address);

                                    editText.setText("");
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"olmadı",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }else {
                    ActivityCompat.requestPermissions(MainActivity2.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    Toast.makeText(getApplicationContext(),"Bilgilendirme mesajı",Toast.LENGTH_LONG).show();
                }
                rv.setVisibility(View.VISIBLE);
            }



        });

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                city=editText.getText().toString();
               //getWeather(city);
                address=editText.getText().toString();
                getWeather(address);

               editText.setText("");


            }
        });
        button.performClick();
    }




    private void getWeather(String cityname){
        //String url="http://api.weatherapi.com/v1/forecast.json?key=9d9fb049d48245ecab0124510213108&q="+ cityname +"&days=1&aqi=yes&alerts=yes";
        //String url="http://api.weatherapi.com/v1/current.json?key=9d9fb049d48245ecab0124510213108&q="+ cityname +"&aqi=yes";
        String url="http://api.weatherapi.com/v1/forecast.json?key=9d9fb049d48245ecab0124510213108&q="+ cityname +"&days=3&aqi=yes&alerts=yes";

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity2.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String   temparature = response.getJSONObject("current").getString("temp_c");
                    textView.setText(temparature );
                    //System.out.println(temparature);
                    int isday=response.getJSONObject("current").getInt("is_day");
                    String condi=response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String condiicon=response.getJSONObject("current").getJSONObject("condition").getString("icon");
                   // String havaa=response.getJSONObject("current").getJSONObject("air_quality").getString("pm2_5");
                    String havaa2=response.getJSONObject("current").getJSONObject("air_quality").getString("pm10");
                    //int sonuc=Integer.parseInt(havaa);
                  //  String havaa=response.getJSONObject("current").getJSONObject("air_quality").getInt("pm10");
                    //String sonuc=Integer.parseInt(havaa);
                    hava.setText(havaa2);
                    String sehir=response.getJSONObject("location").getString("name");
                     sehirad.setText(sehir);
                    Picasso.get().load("https://".concat(condiicon)).into(ıcon);
                    //Picasso.get().load("https://cdn.weatherapi.com/weather/64x64/day/122.png").into(ıcon);

                    condition.setText(condi);
                   // System.out.println(condi);
                    String hissedilenn = response.getJSONObject("current").getString("feelslike_c");
                    hissedilen.setText(hissedilenn + "°C");
                    String nemm = response.getJSONObject("current").getString("humidity");
                    nem.setText(nemm + "%");
                    String windd = response.getJSONObject("current").getString("wind_kph");
                    wind.setText(windd + "km/s");
                    String basıncc = response.getJSONObject("current").getString("pressure_mb");
                    basınc.setText(basıncc + "mbar");
                    String bulutt = response.getJSONObject("current").getString("cloud");
                    bulut.setText(bulutt + "%");
                    String uvv = response.getJSONObject("current").getString("uv");
                    uv.setText(uvv);
                   // String havaa = response.getJSONObject("current").getJSONObject("air_quality").getString("pm2_5");


                    //hava.setText(havaa);
                    JSONObject forecast=response.getJSONObject("forecast");
                    JSONObject forcas=forecast.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray houarray=forcas.getJSONArray("hour");
                    //***********************
                    JSONObject forecastt=response.getJSONObject("forecast");
                    JSONObject forcass=forecastt.getJSONArray("forecastday").getJSONObject(0);
                    JSONObject forcass2=forecastt.getJSONArray("forecastday").getJSONObject(1);
                    JSONObject forcass3=forecastt.getJSONArray("forecastday").getJSONObject(2);
                    JSONObject houarrayy2=forcass2.getJSONObject("day");
                    JSONObject houarrayy3=forcass3.getJSONObject("day");
                    JSONObject houarrayy=forcass.getJSONObject("day");

                    JSONObject havadurumu=houarrayy.getJSONObject("condition");
                    JSONObject havadurumu2=houarrayy2.getJSONObject("condition");
                    JSONObject havadurum3=houarrayy3.getJSONObject("condition");
                   // JSONObject hourobjj=houarrayy.getJSONObject(0);
                    String condition=havadurumu.getString("text");
                    String yukarı=houarrayy.getString("maxtemp_c");
                    String asağı=houarrayy.getString("mintemp_c");
                    String condition2=havadurumu2.getString("text");
                    String yukarı2=houarrayy2.getString("maxtemp_c");
                    String asağı2=houarrayy2.getString("mintemp_c");
                    String condition3=havadurum3.getString("text");
                    String yukarı3=houarrayy3.getString("maxtemp_c");
                    String asağı3=houarrayy3.getString("mintemp_c");
                    String ımage=havadurumu.getString("icon");
                    String ımage2=havadurumu2.getString("icon");
                    String ımage3=havadurum3.getString("icon");
                    Picasso.get().load("https://".concat(ımage)).into(bugunıcon);
                    Picasso.get().load("https://".concat(ımage2)).into(yarınıcon);
                    Picasso.get().load("https://".concat(ımage3)).into(yarınıcon2);
                    yarın.setText("Bugün-"+condition);
                    yarın2.setText(yukarı+"/"+asağı);
                    yarın3.setText("Yarın-"+condition2);
                    yarın4.setText(yukarı2+"/"+asağı2);
                    yarın5.setText("Sonraki gün-"+condition3);
                    yarın6.setText(yukarı3+"/"+asağı3);
                   // System.out.println(temperr);

                    for(int i=0;i<houarray.length();i++){
                        JSONObject hourobj=houarray.getJSONObject(i);
                        String time=hourobj.getString("time");
                        String temper=hourobj.getString("temp_c");
                        String img=hourobj.getJSONObject("condition").getString("icon");
                        String wind=hourobj.getString("wind_kph");
                        weatherModelArrayList.add(new WeatherModel(time,temper,img,wind));

                    }
                    weatherAdapter.notifyDataSetChanged();

                    //JSONObject forcast=yarınn.getJSONArray("forecastday");
                    //JSONArray houarrayy=forcast.getJSONArray("day");
                    //String time=houarrayy.getString("maxtemp_c");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity2.this,"Geçerli bir Şehir adı giriniz!",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }



}