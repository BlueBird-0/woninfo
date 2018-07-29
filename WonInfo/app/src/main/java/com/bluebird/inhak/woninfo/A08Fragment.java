package com.bluebird.inhak.woninfo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

/**
 * Created by InHak on 2017-12-31.
 */

public class A08Fragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_08_fragment, container, false);

        SubsamplingScaleImageView imageView1 = (SubsamplingScaleImageView)view.findViewById(R.id.a_08_imgView);
        imageView1.setImage(ImageSource.resource(R.drawable.a_08_img_campus_map));

        return view;
    }
}
