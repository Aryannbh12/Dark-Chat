package com.example.dark_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class Phone_verification extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null)
        {
            Intent in = new Intent(Phone_verification.this,Chat_Activity.class);
            startActivity(in);
            finishAffinity();
        }

        Button btn = findViewById(R.id.Submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Phone_verification.this, OTP_verification.class);
                EditText et = findViewById(R.id.PhoneNumber);
                String Phno = et.getText().toString();
                in.putExtra("phnoNumber",Phno);
                startActivity(in);
            }
        });
    }
}