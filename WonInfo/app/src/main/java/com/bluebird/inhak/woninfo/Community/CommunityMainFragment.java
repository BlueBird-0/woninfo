package com.bluebird.inhak.woninfo.Community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bluebird.inhak.woninfo.R;

public class CommunityMainFragment extends Fragment{

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_main_fragment, container, false);
/*

        button1 = (Button)view.findViewById(R.id.unbtn);
        button2 = (Button)view.findViewById(R.id.proudbtn);
        button3 = (Button)view.findViewById(R.id.freebtn);
        button4 = (Button)view.findViewById(R.id.foodbtn);
        button5 = (Button)view.findViewById(R.id.hibtn);
        button6 = (Button)view.findViewById(R.id.busbtn);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment();
                loadFragment(fragment);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment2();
                loadFragment(fragment);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager3 = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment3();
                loadFragment(fragment);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager testlistview = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment4();
                loadFragment(fragment);

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager a_15_fragment = getFragmentManager();
                Fragment fragment = null;
                fragment = new A15Fragment();
                loadFragment(fragment);

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager a_24_fragment = getFragmentManager();
                Fragment fragment = null;
                fragment = new A24Fragment();
                loadFragment(fragment);

            }
        });
    */
        return view;
    }

    private boolean loadFragment(Fragment fragment)
    {
        Log.d("test001", "1111");
        //switching fragment
        if(fragment != null)
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();
            Log.d("test001", "2222");
            return true;
        }
        return false;
    }

}
