package com.example.dark_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        int countdown_timer = 4000;
        CountDownTimer tim = new CountDownTimer(countdown_timer, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent in = new Intent(MainActivity.this,Phone_verification.class);
                startActivity(in);
            }
        }.start();
    }
}