package com.bluebird.inhak.woninfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Reserve extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_reserve);

        Button button =(Button)findViewById(R.id.reserve_btn_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PackageManager pm = getPackageManager();
                    pm.getApplicationInfo("com.yebigun4.ex&hl=ko", PackageManager.GET_META_DATA);
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.yebigun4.ex&hl=ko");
                    startActivity(launchIntent);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.yebigun4.ex&hl=ko"));
                    startActivity(intent);
                }


            }
        });
    }
}
