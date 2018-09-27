package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InHak on 2017-12-31.
 */

public class LoginActivity extends Activity{
    private EditText edit_id;
    private EditText edit_pw;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);

        edit_id= (EditText)findViewById(R.id.login_edit_id);
        edit_pw= (EditText)findViewById(R.id.login_edit_pw);
        final Button login_submit = (Button)findViewById(R.id.login_btn_submit);


        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.checkLoggedin();
                UserManager.loginUser(edit_id.getText().toString(), edit_pw.getText().toString());
                finish();
              /*  if(user != null){
                    String name = user.getDisplayName();
                    String email=user.getEmail();
                    // Uri photoUrl = user.getPhotoUrl();

                    boolean emailVerified = user.isEmailVerified();             //이메일 인증
                */

                }

            }
        );}
}