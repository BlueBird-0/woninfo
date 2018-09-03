package com.bluebird.inhak.woninfo.Dictionary.A01Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bluebird.inhak.woninfo.Expandable;
import com.bluebird.inhak.woninfo.R;

/**
 * Created by InHak on 2017-12-31.
 * 원광대학교 이용방법
 */

public class A01Fragment extends Fragment {
    private WebView webView;
    private WebSettings webSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_01_fragment, container, false);

        for(int i=0; i<2;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_01_exl0+i, R.id.a_01_exlbtn0+i, R.id.a_01_exlimg0+i);
        }

        return view;
    }
}
