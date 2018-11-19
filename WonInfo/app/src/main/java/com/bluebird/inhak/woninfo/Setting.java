package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Setting extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.setting);

        Intent intent = new Intent(this.getIntent());
        if(UserManager.checkLoggedin()==true) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            TextView DisplayName = (TextView) findViewById(R.id.setting_nickname);
            DisplayName.setText(user.getDisplayName());
            TextView EmailAdress = (TextView) findViewById(R.id.setting_email_textview);
            EmailAdress.setText(user.getEmail());
            ImageView imageView = (ImageView) findViewById(R.id.setting_imageView);
            if (user.getPhotoUrl() != null) {
                Glide.with(getWindow().getDecorView().getRootView()).load(user.getPhotoUrl()).thumbnail(0.1f).into(imageView);
            }
        }
        ConstraintLayout setAlam = (ConstraintLayout) findViewById(R.id.setting_btn1);
        ConstraintLayout notify = (ConstraintLayout) findViewById(R.id.setting_btn2);
        ConstraintLayout community_rule = (ConstraintLayout) findViewById(R.id.setting_btn3);
        ConstraintLayout personalinfo = (ConstraintLayout) findViewById(R.id.setting_btn4);
        ConstraintLayout appversion = (ConstraintLayout) findViewById(R.id.setting_btn5);
        ConstraintLayout agreement = (ConstraintLayout) findViewById(R.id.setting_btn6);
        ConstraintLayout userleave = (ConstraintLayout) findViewById(R.id.setting_btn7);

        setAlam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingAlarm.class));
            }
        });

        community_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CommunitySettingRules.class));
            }
        });


    }

}