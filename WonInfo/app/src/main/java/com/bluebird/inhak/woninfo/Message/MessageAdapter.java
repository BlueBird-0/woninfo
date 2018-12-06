package com.bluebird.inhak.woninfo.Message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<MessageItem> messageList;
    private AppCompatActivity activity;
    MessageItem message;

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView boardname;
        public TextView content;
        public TextView date;

        public MessageViewHolder(View view) {
            super(view);
            layout = (ConstraintLayout)view.findViewById(R.id.user_message_box);
            boardname = (TextView) view.findViewById(R.id.user_message_broadname);
            content= (TextView) view.findViewById(R.id.user_message_content);
            date = (TextView) view.findViewById(R.id.user_message_date);
        }
    }

    public MessageAdapter(List<MessageItem> messageList, AppCompatActivity activity){
        this.messageList = messageList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_message_list_item, viewGroup, false);

        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, int position) {
        message = messageList.get(position);
        messageViewHolder.boardname.setText(message.getNickname());
        messageViewHolder.content.setText(message.getLastMessage());
        Format formatter = new SimpleDateFormat("HH:mm");
        String dateStr = formatter.format(message.getDate().getApproximateDate());
        messageViewHolder.date.setText(dateStr);

        messageViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, MessageRoomActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("Bundle", message);
                intent.putExtras(args);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
