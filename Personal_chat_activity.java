package com.example.dark_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Personal_chat_activity extends AppCompatActivity {

    String Uname,Uid;
    String currentUser;
    ImageView bt;
    EditText et;
    String senderRoom,receiverRoom,mes;
    FirebaseDatabase fd;
    MessageAdapter ma;
    ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat_activity);


        Uname = getIntent().getStringExtra("Uname");
        Uid = getIntent().getStringExtra("Uid");
        currentUser = FirebaseAuth.getInstance().getUid();

        messages = new ArrayList<>();
        ma = new MessageAdapter(this,messages);
        RecyclerView rcv = findViewById(R.id.messagerecyclerview);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        senderRoom = currentUser + Uid;
        receiverRoom = Uid + currentUser;

        fd = FirebaseDatabase.getInstance();

        fd.getReference().child("chats")
                .child(senderRoom)
                .child("Message")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            Message message = snapshot1.getValue(Message.class);
                            messages.add(message);
                        }
                        ma.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        bt = findViewById(R.id.SendButton);
        rcv.setAdapter(ma);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et = findViewById(R.id.MessageTextBox);
                mes = et.getText().toString();
                et.setText("");
                Date date = new Date();
                Message message = new Message(mes,currentUser,date.getTime());

                HashMap<String,Object> lastMessage = new HashMap<>();
                lastMessage.put("lastMessage",message.getMessage());
                lastMessage.put("lastMessageTime",date.getTime());

                fd.getReference().child("chats").child(senderRoom).updateChildren(lastMessage);
                fd.getReference().child("chats").child(receiverRoom).updateChildren(lastMessage);

                fd.getReference().child("chats").child(senderRoom).child("Message").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        fd.getReference().child("chats").child(receiverRoom).child("Message").push().setValue(message);
                    }
                });
            }
        });

        getSupportActionBar().setTitle(Uname);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}