package com.bluebird.inhak.woninfo;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by InHak on 2017-12-31.
 */

public class A13Fragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_13_fragment, container, false);

        for(int i=0; i<5;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_13_exl0+i, R.id.a_13_exlbtn0+i, R.id.a_13_exlimg0+i);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.a_13_btn_manual0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://image.wku.ac.kr/2016/09/W-point-ack-list.pdf")));
                        break;
                    case R.id.a_13_btn_manual1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://image.wku.ac.kr/2016/09/W-point-manual.pdf")));
                        break;
                    case R.id.a_13_btn_manual2:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://image.wku.ac.kr/2018/03/w-point-lang-con-table.pdf")));
                        break;
                    case R.id.a_13_btn_manual3:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://image.wku.ac.kr/2018/03/W-point_app_form20180301-1.hwp")));
                        break;
                    case R.id.a_13_btn_manual4:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wku.ac.kr/campus-life/w-point.html")));
                        break;
                }
            }
        };
        for(int i=0; i<5; i++)
        {
            TextView textView = (TextView)view.findViewById(R.id.a_13_btn_manual0+i);
            String string = textView.getText().toString();
            textView.setText(Html.fromHtml("<u>"+string+"</u>"));
            textView.setOnClickListener(onClickListener);
        }
        return view;
    }
}
