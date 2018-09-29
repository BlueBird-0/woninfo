package com.bluebird.inhak.woninfo.Dictionary;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.DBOpenHelper;
import com.bluebird.inhak.woninfo.R;

/**
 * Created by InHak on 2017-12-30.
 */

public class DictionaryMainFragmentNoUse extends Fragment  {
    private DBOpenHelper dbOpenHelper;
    private Cursor cursor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dictionary_main_fragment_nouse, container, false);





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
