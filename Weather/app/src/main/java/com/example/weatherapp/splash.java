package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifImageView;

public class splash extends AppCompatActivity {
  private  static int gecis=5000;
  private ImageView ım;
  GifImageView gf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anima);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent);
                finish();
            }
        },gecis);
    }
}