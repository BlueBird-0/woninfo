package com.bluebird.inhak.woninfo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by InHak on 2017-12-31.
 */

public class A12Fragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_12_fragment, container, false);

        Expandable expandable0 = new Expandable(view, R.id.a_12_exl0, R.id.a_12_exlbtn0, R.id.a_12_exlimg0);
        Expandable expandable1 = new Expandable(view, R.id.a_12_exl1, R.id.a_12_exlbtn1, R.id.a_12_exlimg1);

        return view;
    }
}
