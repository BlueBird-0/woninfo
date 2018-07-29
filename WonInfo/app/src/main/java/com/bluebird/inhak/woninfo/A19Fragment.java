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

public class A19Fragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/search/top/?q=%EC%9B%90%EA%B4%91%EB%8C%80%20%EB%93%9C%EB%A3%A8%EC%99%80")));
                        break;
                }
            }
        };

        TextView textView = (TextView)view.findViewById(R.id.a_19_btn_facebookLink0);
        String string = textView.getText().toString();
        textView.setText(Html.fromHtml("<u>"+string+"</u>"));
        textView.setOnClickListener(onClickListener);


        return view;
    }
}
