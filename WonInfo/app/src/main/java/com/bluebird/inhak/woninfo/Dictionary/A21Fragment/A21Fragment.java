package com.bluebird.inhak.woninfo.Dictionary.A21Fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Expandable;
import com.bluebird.inhak.woninfo.R;

/**
 * Created by InHak on 2017-12-31.
 */

public class A21Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_21_fragment, container, false);

        for(int i=0; i<3;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_21_exl0+i, R.id.a_21_exlbtn0+i, R.id.a_21_exlimg0+i);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.a_21_btn_libraryLink:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://elibrary.wku.ac.kr/WONK/")));
                        break;
                }
            }
        };

        TextView textView = (TextView)view.findViewById(R.id.a_21_btn_libraryLink);
        String string = textView.getText().toString();
        textView.setText(Html.fromHtml("<u>"+string+"</u>"));
        textView.setOnClickListener(onClickListener);


        LinearLayout btn_libraryApp = (LinearLayout)view.findViewById(R.id.a_21_btn_libraryApp);
        btn_libraryApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PackageManager pm = getActivity().getPackageManager();
                    pm.getApplicationInfo("kr.co.libtech.sponge", PackageManager.GET_META_DATA);
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("kr.co.libtech.sponge");
                    startActivity(launchIntent);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=kr.co.libtech.sponge"));
                    startActivity(intent);
                }
            }
        });



        return view;
    }
}
