package com.bluebird.inhak.woninfo.Dictionary.A19Fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Expandable;
import com.bluebird.inhak.woninfo.R;

/**
 * Created by InHak on 2017-12-31.
 */

public class A19Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_19_fragment, container, false);

        for(int i=0; i<2;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_19_exl0+i, R.id.a_19_exlbtn0+i, R.id.a_19_exlimg0+i);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.a_19_btn_facebookLink0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/%EC%9B%90%EA%B4%91%EB%8C%80%ED%95%99%EC%83%9D-%EC%A0%84%ED%95%B4%EB%93%9C%EB%A0%A4%EC%9A%94-538119249591629/")));
                    case R.id.a_19_btn_facebookLink01:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/wongk00/")));
                    case R.id.a_19_btn_facebookLink02:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/WonkwangUniversity/")));
                        break;
                }
            }
        };


        TextView textView = (TextView)view.findViewById(R.id.a_19_btn_facebookLink0);
        String string = textView.getText().toString();
        textView.setText(Html.fromHtml("<u>"+string+"</u>"));
        textView.setOnClickListener(onClickListener);
        TextView textView2 = (TextView)view.findViewById(R.id.a_19_btn_facebookLink01);
        String string2 = textView2.getText().toString();
        textView2.setText(Html.fromHtml("<u>"+string2+"</u>"));
        textView2.setOnClickListener(onClickListener);
        TextView textView3 = (TextView)view.findViewById(R.id.a_19_btn_facebookLink02);
        String string3 = textView3.getText().toString();
        textView3.setText(Html.fromHtml("<u>"+string3+"</u>"));
        textView3.setOnClickListener(onClickListener);


        return view;
    }
}
