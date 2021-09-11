package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class hava extends AppCompatActivity {
   TextView hava,yorum;
   String son;
   ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hava);
        hava=findViewById(R.id.hava);
        yorum=findViewById(R.id.yorum);
        back=findViewById(R.id.back);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String  mealId = bundle.getString("hava");
        hava.setText(mealId+" iyi");


  back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          //Intent intent1=new Intent(getApplicationContext(),MainActivity2.class);
         // startActivity(intent1);
          hava.super.onBackPressed();
      }
  });





        }

    }
