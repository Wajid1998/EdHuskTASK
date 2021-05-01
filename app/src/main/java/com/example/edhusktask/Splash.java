package com.example.edhusktask;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            getSupportActionBar().hide();

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);


                        Intent in = new Intent(Splash.this,SignUp.class);
                        startActivity(in);
                        finish();


                    }catch (InterruptedException e){
                        e.printStackTrace();

                    }
                }
            });
            th.start();
        }
    }