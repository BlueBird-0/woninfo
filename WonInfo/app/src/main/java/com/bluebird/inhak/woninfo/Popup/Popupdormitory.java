package com.bluebird.inhak.woninfo.Popup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bluebird.inhak.woninfo.R;

public class Popupdormitory extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_dormitory);
        Intent intent = new Intent(this.getIntent());

        Button button = (Button) findViewById(R.id.popup_btn_dormitory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://intra.wku.ac.kr"));
                startActivity(intent);
            }
        });

    }
}
