package com.bluebird.inhak.woninfo.A14Fragment;

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

import com.bluebird.inhak.woninfo.Expandable;
import com.bluebird.inhak.woninfo.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by InHak on 2017-12-31.
 */

public class A14Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_14_fragment, container, false);

        for(int i=0; i<3;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_14_exl0+i, R.id.a_14_exlbtn0+i, R.id.a_14_exlimg0+i);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.a_14_btn_manual0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://intra.wku.ac.kr/WEB/manual/o365/o365_User_Manual_20160502.pdf")));
                        break;
                }
            }
        };
        for(int i=0; i<1; i++)
        {
            TextView textView = (TextView)view.findViewById(R.id.a_14_btn_manual0+i);
            String string = textView.getText().toString();
            textView.setText(Html.fromHtml("<u>"+string+"</u>"));
            textView.setOnClickListener(onClickListener);
        }
        TextView textView = (TextView)view.findViewById(R.id.a_14_btn_officeLink);
        String string = textView.getText().toString();
        textView.setText(Html.fromHtml("<u>"+string+"</u>"));
        textView.setOnClickListener(onClickListener);



        SubsamplingScaleImageView imageView0 = (SubsamplingScaleImageView)view.findViewById(R.id.a_14_img_officesetting);
        imageView0.setImage(ImageSource.resource(R.drawable.a_14_img_officesetting));

        return view;
    }
}
