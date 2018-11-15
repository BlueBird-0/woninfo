package com.bluebird.inhak.woninfo.Community;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluebird.inhak.woninfo.Community.Board1.BoardListFragment;
import com.bluebird.inhak.woninfo.Community.Board2.BoardListFragment2;
import com.bluebird.inhak.woninfo.Community.Board3.BoardListFragment3;
import com.bluebird.inhak.woninfo.Dictionary.A15Fragment.A15Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A24Fragment.A24Fragment;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

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
        final View view = inflater.inflate(R.layout.community_main_fragment, container, false);
        setHasOptionsMenu(true);

        button1 = (ConstraintLayout)view.findViewById(R.id.write2_layout_picture);
        button2 = (ConstraintLayout)view.findViewById(R.id.community_main_2);
        button3 = (ConstraintLayout)view.findViewById(R.id.community_main_3);
        button4 = (ConstraintLayout)view.findViewById(R.id.community_main_4);
        button5 = (ConstraintLayout)view.findViewById(R.id.community_main_5);
        button6 = (ConstraintLayout)view.findViewById(R.id.community_main_6);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.checkLoggedin()==true){
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment();
                loadFragment(fragment);}
                else{

                    View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "로그인 후 이용가능합니다.", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.checkLoggedin()==true){
                FragmentManager fragmentManager2 = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment2();
                loadFragment(fragment);}
                else{

                    View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "로그인 후 이용가능합니다.", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }


            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.checkLoggedin()==true){
                FragmentManager fragmentManager3 = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment3();
                loadFragment(fragment);}
                else{

                    View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "로그인 후 이용가능합니다.", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.checkLoggedin()==true){
                FragmentManager testlistview = getFragmentManager();
                Fragment fragment = null;
               // fragment = new BoardListFragment4();
                loadFragment(fragment);}
                else{

                    View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "로그인 후 이용가능합니다.", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.checkLoggedin()==true){
                FragmentManager a_15_fragment = getFragmentManager();
                Fragment fragment = null;
                fragment = new A15Fragment();
                loadFragment(fragment);}
                else{

                    View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "로그인 후 이용가능합니다.", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }

            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.checkLoggedin()==true){
                FragmentManager a_24_fragment = getFragmentManager();
                Fragment fragment = null;
                fragment = new A24Fragment();
                loadFragment(fragment);}
                else{

                    View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "로그인 후 이용가능합니다.", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }

            }
        });

        return view;
    }

    private boolean loadFragment(Fragment fragment)
    {
        //switching fragment
        if(fragment != null)
        {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_open, 0, 0, R.anim.slide_close);
            fragmentTransaction.add(R.id.main_fragment_container, fragment);
            fragmentTransaction.addToBackStack("menu_community");
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }

}
