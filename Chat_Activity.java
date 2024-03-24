package com.example.dark_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat_Activity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<User> users;
    UserAdapter userAdapter;
    RecyclerView rv;
    StatusAdapter statusAdapter;
    ArrayList<UserStatus> userStatuses;
    RecyclerView srv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        users = new ArrayList<>();
        userStatuses = new ArrayList<>();

        userAdapter = new UserAdapter(this,users);
        statusAdapter = new StatusAdapter(this,userStatuses);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        srv = findViewById(R.id.statusRecyclerView);
        srv.setLayoutManager(linearLayoutManager);
        srv.setAdapter(statusAdapter);
        rv = findViewById(R.id.recyclerView);
        rv.setAdapter(userAdapter);

        database.getReference()
                .child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            User user = snapshot1.getValue(User.class);
                            if (user.getUid().equals(FirebaseAuth.getInstance().getUid()))
                            {

                            }
                            else
                            {
                                users.add(user);
                            }

                        }
                        userAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Search is Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Settings:
                Toast.makeText(this, "Setting is Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}