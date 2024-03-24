package com.example.dark_chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Profile_acticity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    ImageView iv;
    Button btn;
    EditText et;
    Uri SelectedImage;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acticity);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        pd = new ProgressDialog(this);
        pd.setMessage("Creating Profile.....");
        pd.setCancelable(false);
        iv = findViewById(R.id.ProfileImage);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setAction(Intent.ACTION_GET_CONTENT);
                in.setType("image/*");
                startActivityForResult(in,45);
            }
        });

        btn = findViewById(R.id.ProSubBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et = findViewById(R.id.Name);
                String name = et.getText().toString();

                if (name.isEmpty())
                {
                    et.setError("Enter A Name");
                    return;
                }
                pd.show();
                if(SelectedImage != null)
                {
                    StorageReference ref = storage.getReference().child("Profiles").child(auth.getUid());
                    ref.putFile(SelectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String ImageUri = uri.toString();
                                        String uid = auth.getUid();

                                        String Uname = name;
                                        String phno = auth.getCurrentUser().getPhoneNumber();

                                        User us = new User(uid,phno,Uname,ImageUri);

                                        database.getReference()
                                                .child("users")
                                                .child(uid)
                                                .setValue(us)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        pd.dismiss();
                                                        Intent intent = new Intent(Profile_acticity.this, Chat_Activity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    });
                }
                else
                {
                    String ImageUri = "No Image";
                    String uid = auth.getUid();

                    String Uname = name;
                    String phno = auth.getCurrentUser().getPhoneNumber();

                    User us = new User(uid,phno,Uname,ImageUri);

                    database.getReference()
                            .child("users")
                            .child(uid)
                            .setValue(us)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Intent intent = new Intent(Profile_acticity.this, Chat_Activity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null)
        {
            if(data.getData() != null)
            {
                iv = findViewById(R.id.ProfileImage);
                iv.setImageURI(data.getData());
                SelectedImage = data.getData();
            }
        }
    }
}