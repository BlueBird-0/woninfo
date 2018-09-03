package com.bluebird.inhak.woninfo;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by InHak on 2017-12-31.
 */

public class A08Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_08_fragment, container, false);

        SubsamplingScaleImageView imageView1 = (SubsamplingScaleImageView) view.findViewById(R.id.a_08_imgView);
        imageView1.setImage(ImageSource.resource(R.drawable.a_08_img_campus_map));


        ImageButton googlelink = (ImageButton) view.findViewById(R.id.a_08_bnt_google_map);

        googlelink.setOnClickListener(new View.OnClickListener(){

            @Override

            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("https://www.google.co.kr/maps/place/%EC%9B%90%EA%B4%91%EB%8C%80%ED%95%99%EA%B5%90/@35.9700678,126.9531279,17z/data=!4m12!1m6!3m5!1s0x357015527a316527:0x37f3a7800fd9ee31!2z7JuQ6rSR64yA7ZWZ6rWQ!8m2!3d35.9703446!4d126.9547563!3m4!1s0x357015527a316527:0x37f3a7800fd9ee31!8m2!3d35.9703446!4d126.9547563?hl=ko"));

                startActivity(intent);

            }


        });

        return view;
    }
}
