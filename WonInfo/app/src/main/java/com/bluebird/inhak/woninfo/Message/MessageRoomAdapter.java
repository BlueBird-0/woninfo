package com.bluebird.inhak.woninfo.Message;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageRoomAdapter extends RecyclerView.Adapter<MessageRoomAdapter.MessageViewHolder> {
    private List<MessageItem> messageList;
    private AppCompatActivity activity;

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView body;
        public TextView date;

        public MessageViewHolder(View view) {
            super(view);
            body = (TextView) view.findViewById(R.id.user_message_body);
            date = (TextView) view.findViewById(R.id.user_message_date);
        }
    }

    public MessageRoomAdapter(List<MessageItem> messageList, AppCompatActivity activity){
        this.messageList = messageList;
        this.activity = activity;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(messageList.get(viewType).getUid().equals(UserManager.firebaseUser.getUid())) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.user_message_chat1_item, viewGroup, false);
            return new MessageViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.user_message_chat2_item, viewGroup, false);
            return new MessageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int position) {
        MessageItem message = messageList.get(position);
        messageViewHolder.body.setText(message.getLastMessage());
        //messageViewHolder.body.setText(message.getNickname());

        Date date = message.getDate().getApproximateDate();
        Format formatter = new SimpleDateFormat("HH:mm");
        String dateStr = formatter.format(date);
        Log.d("test098", dateStr);
        messageViewHolder.date.setText(dateStr);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
