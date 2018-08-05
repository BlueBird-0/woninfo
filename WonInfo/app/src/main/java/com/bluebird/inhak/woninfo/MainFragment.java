package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.A08Fragment.A08Fragment;
import com.bluebird.inhak.woninfo.A15Fragment.A15Fragment;
import com.bluebird.inhak.woninfo.A16Fragment.A16Fragment;

/**
 * Created by InHak on 2017-12-30.
 */

public class MainFragment extends Fragment  {
    private DBOpenHelper dbOpenHelper;
    private Cursor cursor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);


        final Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);

        final RelativeLayout relativeLayout0 = (RelativeLayout)view.findViewById(R.id.menu_dictionaryRayout);
        relativeLayout0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout0.startAnimation(shake);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.slide_left, R.animator.slide_right);
                fragmentTransaction.add(R.id.view_fragment, new MenuDictionaryFragment());
                fragmentTransaction.addToBackStack("menu_dictionary");
                fragmentTransaction.commit();
            }
        });

        final RelativeLayout relativeLayout1 = (RelativeLayout)view.findViewById(R.id.menu_mapRayout);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout1.startAnimation(shake);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.slide_left, R.animator.slide_right);
                fragmentTransaction.add(R.id.view_fragment, new A08Fragment());
                fragmentTransaction.addToBackStack("menu_map");
                fragmentTransaction.commit();
            }
        });

        final RelativeLayout relativeLayout2 = (RelativeLayout)view.findViewById(R.id.menu_calenderRayout);
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout2.startAnimation(shake);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.slide_left, R.animator.slide_right);
                fragmentTransaction.add(R.id.view_fragment, new A15Fragment());
                fragmentTransaction.addToBackStack("menu_map");
                fragmentTransaction.commit();
            }
        });


        final RelativeLayout relativeLayout3 = (RelativeLayout)view.findViewById(R.id.menu_foodRayout);
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout3.startAnimation(shake);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.slide_left, R.animator.slide_right);
                fragmentTransaction.add(R.id.view_fragment, new A16Fragment());
                fragmentTransaction.addToBackStack("menu_map");
                fragmentTransaction.commit();
            }
        });

        final RelativeLayout relativeLayout4 = (RelativeLayout)view.findViewById(R.id.menu_libraryRayout);
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout4.startAnimation(shake);
                try{
                    PackageManager pm = getActivity().getPackageManager();
                    pm.getApplicationInfo("mirtech.slima.WONK2", PackageManager.GET_META_DATA);
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("mirtech.slima.WONK2");
                    startActivity(launchIntent);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=mirtech.slima.WONK2"));
                    startActivity(intent);
                }
            }
        });


        /*
        final RelativeLayout relativeLayout5 = (RelativeLayout)view.findViewById(R.id.menu_popup);
        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout5.startAnimation(shake);
                startActivity(new Intent(getActivity(), Popup01Activity.class));
                ((DrawerLayout)getActivity().findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
            }
        });
    */

        //앱이 처음 시작될 때, 튜토리얼 페이지 출력
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.SETTING_FILENAME), Activity.MODE_PRIVATE);
        if ( sharedPreferences.getBoolean(getString(R.string.TUTORIAL_START), true) == true)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.TUTORIAL_START), false);
            editor.commit();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            try {
                Class t = Class.forName("com.bluebird.inhak.woninfo."+ "Tutorial" +"Fragment");
                Fragment fragment = (Fragment)t.newInstance();

                fragmentTransaction.replace(R.id.view_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }catch(Exception e) {}
        }

        if (savedInstanceState != null) {
            int j = getArguments().getInt("index");
            Toast.makeText(getActivity(), String.valueOf(j), Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}
