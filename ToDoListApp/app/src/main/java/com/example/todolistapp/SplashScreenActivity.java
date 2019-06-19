package com.example.todolistapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(TaskCategore.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#665FE8"))
                .withFooterText("@2019 sara_oheda")
                .withAfterLogoText("THE PLACE TO MAKE DEHREENCE")
                .withLogo(R.drawable.ic_checklist2);

        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
