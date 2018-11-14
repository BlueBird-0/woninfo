package com.bluebird.inhak.woninfo.Popup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.BottomNavigationViewHelper;
import com.bluebird.inhak.woninfo.Home.HomeMainFragment;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;

public class PopupLogout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_logout);

        Button button1 = findViewById(R.id.popup_logout_canclebtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        Button button2 = findViewById(R.id.popup_logout_okbtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.logoutUser();
                MainActivity.setBottomBarMenu(R.id.bottom_bar_menu_home);
                /*
                UserManager.logoutUser();
                BottomNavigationView bottomNavigationView = (BottomNavigationView) v.getRootView().findViewById(R.id.bottom_navigation_view);
                BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                bottomNavigationView.findViewById(R.id.bottom_bar_menu_home).callOnClick();
*/


               /*
                   Fragment mainFragment = new HomeMainFragment();
                    getSupportFragmentManager()
                        .beginTransaction()
                        .commit();
*/
                    finish();




            }
        });
    }
}


