package com.rppburhanpur.fuel2door;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(new SplashRunner(this),3000);

    }
}
class SplashRunner implements Runnable{
    private Intent startActivityIntent;
    private Splash splash;

    public SplashRunner(Splash splash) {
        this.splash = splash;
    }

    @Override
    public void run() {
        startActivityIntent = new Intent(splash,User_Registration.class);
        splash.startActivity(startActivityIntent);
        splash.finish();
    }
}
