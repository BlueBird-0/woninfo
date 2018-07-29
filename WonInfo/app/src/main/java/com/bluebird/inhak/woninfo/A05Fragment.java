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

import com.github.chuross.library.ExpandableLayout;

/**
 * Created by InHak on 2017-12-31.
 */

public class A05Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_05_fragment, container, false);

        for(int i=0; i<6;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_05_exl0+i, R.id.a_05_exlbtn0+i, R.id.a_05_exlimg0+i);
        }

        final ExpandableLayout expandableLayout = (ExpandableLayout)view.findViewById(R.id.a_03_exl0);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.a_05_btn_buslink:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wku.ac.kr/campus-life/school-bus-lines/inter-city-lines.html")));
                        break;
                    case R.id.a_05_btn_wonpuslink:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.kimsj.kimsj.wonpus")));
                        break;
                }
            }
        };

        TextView textView1 = (TextView)view.findViewById(R.id.a_05_btn_buslink);
        String string1 = textView1.getText().toString();
        textView1.setText(Html.fromHtml("<u>"+string1+"</u>"));
        textView1.setOnClickListener(onClickListener);

        TextView textView2 = (TextView)view.findViewById(R.id.a_05_btn_wonpuslink);
        String string2 = textView2.getText().toString();
        textView2.setText(Html.fromHtml("<u>"+string2+"</u>"));
        textView2.setOnClickListener(onClickListener);
        return view;
    }
}
