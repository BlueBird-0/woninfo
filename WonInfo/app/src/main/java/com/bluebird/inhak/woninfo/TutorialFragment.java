package com.bluebird.inhak.woninfo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by InHak on 2017-12-31.
 * 원광대학교 이용방법
 */

public class TutorialFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutorial_fragment, container, false);

        ImageView imageView = (ImageView)view.findViewById(R.id.tutorial_close_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorialFragment.super.getActivity().onBackPressed();
            }
        });

        return view;
    }
}
