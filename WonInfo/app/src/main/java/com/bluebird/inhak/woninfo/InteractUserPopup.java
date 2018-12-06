package com.bluebird.inhak.woninfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bluebird.inhak.woninfo.Message.MessageRoomActivity;
import com.bluebird.inhak.woninfo.Popup.PopupEnd;

public class InteractUserPopup extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_interactuser_popup);

        Button sendMessageBtn = (Button)findViewById(R.id.popup_sendmessage_btn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MessageRoomActivity.class));
            }
        });
    }
}
