package com.example.dark_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dark_chat.databinding.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int MESSAGE_SENTER = 1;
    final int MESSAGE_RECEIVEER = 2;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }


    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId()))
        {
            return MESSAGE_SENTER;
        }
        else
        {
            return MESSAGE_RECEIVEER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MESSAGE_SENTER )
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_sent,parent,false);
            return new SentViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_recive,parent,false);
            return new ReceiveViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if(holder.getClass() == SentViewHolder.class)
        {
            SentViewHolder viewHolder = (SentViewHolder)holder;
            viewHolder.binding.Message.setText(message.getMessage());
        }
        else
        {
            ReceiveViewHolder receiveViewHolder = (ReceiveViewHolder)holder;
            receiveViewHolder.binding.Message.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder
    {
        MessageSentBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MessageSentBinding.bind(itemView);
        }
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder
    {
        MessageReciveBinding binding;
        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MessageReciveBinding.bind(itemView);
        }
    }

}
