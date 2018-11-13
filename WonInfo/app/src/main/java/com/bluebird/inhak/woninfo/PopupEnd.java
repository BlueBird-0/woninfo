package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PopupEnd extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_ending);

        Button canclebutton = (Button)findViewById(R.id.popup_ending_canclebtn);
        Button agreebutton = (Button)findViewById(R.id.popup_ending_okbtn);

        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        agreebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });
    }
}
