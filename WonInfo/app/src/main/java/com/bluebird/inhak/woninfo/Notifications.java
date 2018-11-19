package com.bluebird.inhak.woninfo;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Handler;
/*
public class Notifications extends IntentService {

    @Override
    public void onCreate() {
        super.onCreate();
        EditText mEditText;
        Button mButton;
    }

    public Notifications(){
        super("notificationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        switch(intent.getAction()){
            case "left":
                Handler leftHandler = new Handler(Looper.getMainLooper());
                leftHandler.post(new Runnable(){
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"you clicked the left button",Toast.LENGTH_LONG).show();
                        }
                });
                break;

            case "right":
                Handler rightHandler = new Handler(Looper.getMainLooper());
                rightHandler.post(new Runnable(){
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"you clicked the right button",Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }

    }
}
*/