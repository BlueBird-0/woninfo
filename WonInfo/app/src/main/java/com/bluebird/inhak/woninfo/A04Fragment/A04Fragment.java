package com.bluebird.inhak.woninfo.A04Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluebird.inhak.woninfo.R;

/**
 * Created by InHak on 2017-12-31.
 */

public class A04Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_04_fragment, container, false);

        return view;
    }
}