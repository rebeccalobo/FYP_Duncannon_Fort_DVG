package com.example.fyptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    private static int msplashTimeOut=5000;
    private static int delay =2000;
    private Animation fade_in;
    private ImageView logo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo1 = findViewById(R.id.logo1);

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        logo1.startAnimation(fade_in);

        new Handler().postDelayed(() -> {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();

        },msplashTimeOut);

    }
}
