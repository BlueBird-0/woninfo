package com.bluebird.inhak.woninfo.Dictionary.A24Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bluebird.inhak.woninfo.Expandable;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;

/**
 * Created by InHak on 2017-12-31.
 */

public class A24Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_24_fragment, container, false);

        //메뉴 레이아웃 2개 + 2번째 메뉴안에 노선별 레이아웃 3개
        for(int i=0; i<5;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_24_exl0+i, R.id.a_24_exlbtn0+i, R.id.a_24_exlimg0+i);
        }

        final String FragmentTitle[] = new String[]{"전주", "군산", "김제/정읍/부안", "대전/계룡/논산",
                "익산역/송학동/모현동", "영등동/부송동", "동산동", "방학중"};
        for(int i=0; i<8; i++)
        {
            RelativeLayout btn = (RelativeLayout)view.findViewById(R.id.a_24_routeBtn0 + i);
            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    try {
                        Class t = Class.forName("com.bluebird.inhak.woninfo."+ "A24Fragment."+"A24ListFragment");
                        Bundle bundle = new Bundle();
                        bundle.putString("area", FragmentTitle[finalI]);

                        Fragment fragment = (Fragment)t.newInstance();
                        fragment.setArguments(bundle);

                        fragmentTransaction.replace(R.id.view_fragment, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((MainActivity)getActivity()).getSupportActionBar().setTitle(FragmentTitle[finalI]);

                    }catch(Exception e) {}
                }
            });
        }

        return view;
    }
}
