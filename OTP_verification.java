package com.example.dark_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP_verification extends AppCompatActivity {

    FirebaseAuth auth;
    TextView ph,OTP_wrong_message;
    String verificationID, phonenumber;
    EditText OTP;
    ProgressDialog pd;
    String z;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        getSupportActionBar().hide();
        z = getIntent().getStringExtra("phnoNumber");
        TextView tx = findViewById(R.id.TextViewVerify);
        tx.setText(z);
        OTP_wrong_message = findViewById(R.id.OTP_Error);

        phonenumber = getIntent().getStringExtra("phnoNumber");

        auth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);
        pd.setMessage("Sending OTP...");
        pd.setCancelable(false);
        pd.show();

        PhoneAuthOptions opt = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91"+phonenumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        OTP_wrong_message.setText(e.getMessage());
                        pd.dismiss();

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        pd.dismiss();
                        verificationID = s;
                    }


                }).build();

        PhoneAuthProvider.verifyPhoneNumber(opt);

        OTP = findViewById(R.id.OTP);
        OTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String csl = OTP.getText().toString();
                if (csl.length() == 6)
                {
                    String otp = OTP.getText().toString();
                    PhoneAuthCredential pac = PhoneAuthProvider.getCredential(verificationID,otp);
                    auth.signInWithCredential(pac).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(OTP_verification.this,"Successfull",Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(OTP_verification.this, Profile_acticity.class);
                                startActivity(in);
                                finishAffinity();
                            }
                            else
                            {
                                OTP_wrong_message = findViewById(R.id.OTP_Error);
                                OTP_wrong_message.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                else if (csl.length() > 6)
                {
                    String combine_OTP="";
                    for (int i=0;i<6;i++)
                    {
                        char ch = csl.charAt(i);
                        combine_OTP += ch;
                    }
                    OTP.setText(combine_OTP);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button btn = findViewById(R.id.SubbmitBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.OTP);
                String OTPp = et.getText().toString();
                PhoneAuthCredential pac = PhoneAuthProvider.getCredential(verificationID,OTPp);

                auth.signInWithCredential(pac).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(OTP_verification.this, "Verification Successfull", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(OTP_verification.this, Profile_acticity.class);
                            startActivity(in);
                            finishAffinity();
                        }
                        else
                        {
                            Toast.makeText(OTP_verification.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

}