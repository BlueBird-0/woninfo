package com.bluebird.inhak.woninfo.Dictionary.A24Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.bluebird.inhak.woninfo.R;

public class A24PopupFragment extends Activity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.a_24_popup_activity);


    }
}
