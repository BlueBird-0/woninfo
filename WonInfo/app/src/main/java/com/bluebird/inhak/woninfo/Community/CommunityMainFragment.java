package com.bluebird.inhak.woninfo.Community;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluebird.inhak.woninfo.Dictionary.A15Fragment.A15Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A24Fragment.A24Fragment;
import com.bluebird.inhak.woninfo.R;

public class CommunityMainFragment extends Fragment{
    ConstraintLayout button1;
    ConstraintLayout button2;
    ConstraintLayout button3;
    ConstraintLayout button4;
    ConstraintLayout button5;
    ConstraintLayout button6;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_main_fragment, container, false);
        setHasOptionsMenu(true);

        button1 = (ConstraintLayout)view.findViewById(R.id.community_main_1);
        button2 = (ConstraintLayout)view.findViewById(R.id.community_main_2);
        button3 = (ConstraintLayout)view.findViewById(R.id.community_main_3);
        button4 = (ConstraintLayout)view.findViewById(R.id.community_main_4);
        button5 = (ConstraintLayout)view.findViewById(R.id.community_main_5);
        button6 = (ConstraintLayout)view.findViewById(R.id.community_main_6);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment1();
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

        return view;
    }

    private boolean loadFragment(Fragment fragment)
    {
        //switching fragment
        if(fragment != null)
        {
            getFragmentManager().beginTransaction().addToBackStack(null);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
