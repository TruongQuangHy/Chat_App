package com.example.app_chat.adapters;

import android.graphics.Bitmap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_chat.databinding.ItemContainerReceivedMessageBinding;
import com.example.app_chat.databinding.ItemContainerUserBinding;
import com.example.app_chat.listeners.UserListener;
import com.example.app_chat.models.User;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_chat.databinding.ItemContainerSendMessageBinding;
import com.example.app_chat.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> chatMessage;
    private final Bitmap receivedProfileImage;
    private String senderId;

    public static final int VIEW_TYPE_SENT =1 ;
    public static final int VIEW_TYPE_RECEIVED =2 ;

    public ChatAdapter(List<ChatMessage> chatMessage, Bitmap receivedProfileImage, String senderId) {
        this.chatMessage = chatMessage;
        this.receivedProfileImage = receivedProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemContainerSendMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else{
            return new SentMessageViewHolder(
                    ItemContainerSendMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==VIEW_TYPE_SENT){
            ((SentMessageViewHolder)holder).setData(chatMessage.get(position));

        }else{
            ((ReceivedMessageViewHolder)holder).setDate(chatMessage.get(position), receivedProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessage.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else{
            return VIEW_TYPE_RECEIVED;
        }

    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerSendMessageBinding binding;
        SentMessageViewHolder(ItemContainerSendMessageBinding itemContainerSendMessageBinding){

            super(itemContainerSendMessageBinding.getRoot());
            binding = itemContainerSendMessageBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);

        }
    }
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerReceivedMessageBinding binding;

        ReceivedMessageViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding){
            super(itemContainerReceivedMessageBinding.getRoot());
            binding = itemContainerReceivedMessageBinding;

        }
        void setDate(ChatMessage chatMessage, Bitmap receiverProfileImage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            binding.imageProfile.setImageBitmap(receiverProfileImage);
        }
    }
}
