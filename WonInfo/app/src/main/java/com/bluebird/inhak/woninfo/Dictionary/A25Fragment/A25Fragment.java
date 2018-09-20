package com.bluebird.inhak.woninfo.Dictionary.A25Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bluebird.inhak.woninfo.Expandable;
import com.bluebird.inhak.woninfo.ExpandableNew;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

/**
 * Created by InHak on 2017-12-31.
 */

public class A25Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_25_fragment, container, false);

        //메뉴 레이아웃 2개 + 2번째 메뉴안에 노선별 레이아웃 3개
        for(int i=0; i<4;i++)
        {
         ExpandableNew expandable = new ExpandableNew(view, R.id.a_25_exl0+i,R.id.a_25_exlbtn0+i);
        }


        return view;
    }
}
