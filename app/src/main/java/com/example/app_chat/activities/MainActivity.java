package com.example.app_chat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import com.example.app_chat.databinding.ActivityMainBinding;
import com.example.app_chat.utilities.Constants;
import com.example.app_chat.utilities.PrefernceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefernceManager prefernceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefernceManager = new PrefernceManager(getApplicationContext());
        localUserDetails();
        getToken();
        setListeners();
    }
    private void setListeners(){
        binding.imageSignOut.setOnClickListener(v -> signOut());
        binding.fabNewChat.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UserActivity.class));
        });
    }
    private void localUserDetails(){
        binding.textName.setText(prefernceManager.getString(Constants.KEY_NAME));
        byte[] bytes = Base64.decode(prefernceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);

    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }
    private void updateToken(String token){

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constants.KEY_COLLECTION_USERS).document(prefernceManager.getString(Constants.KEY_USER_ID));;
        documentReference.update(Constants.Key_FCM_TOKEN, token)

                .addOnSuccessListener(e -> showToast("Unbable to update token"));
    }
    private void signOut(){
        showToast("Sign Out...");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference= database.collection(Constants.KEY_COLLECTION_USERS).document(
                prefernceManager.getString(Constants.KEY_USER_ID)
        );
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.Key_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {
                    prefernceManager.clear();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> showToast("Unable to sign out"));
    }
}