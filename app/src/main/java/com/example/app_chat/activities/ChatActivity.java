 package com.example.app_chat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app_chat.databinding.ActivityChatBinding;
import com.example.app_chat.models.User;
import com.example.app_chat.utilities.Constants;

 public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private User receivedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceivedDetails();

    }
    private void loadReceivedDetails(){
        receivedUser = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(receivedUser.name);
    }
    private void setListeners(){
        binding.imageBack.setOnClickListener(v -> onBackPressed());

    }
 }
