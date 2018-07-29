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

public class A18Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_18_fragment, container, false);

        Expandable expandable0 = new Expandable(view, R.id.a_18_exl0, R.id.a_18_exlbtn0, R.id.a_18_exlimg0);

        return view;
    }
}
