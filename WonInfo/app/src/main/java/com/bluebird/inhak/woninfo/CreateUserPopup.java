package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.ActionCodeSettings;

public class CreateUserPopup extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_user_activity);

        String email = ((EditText)findViewById(R.id.create_user_email_edit)).getText().toString();
        final String password = ((EditText)findViewById(R.id.create_user_password_edit)).getText().toString();
        final String password_confirm = ((EditText)findViewById(R.id.create_user_confirm_edit)).getText().toString();
        Button create = (Button)findViewById(R.id.create_user_create_btn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //비밀번호 확인
                if(password == password_confirm)
                {
                    // 아이디와 패스워드가 같은지 확인   GUI 수정
                    ActionCodeSettings actionCodeSettings =
                        ActionCodeSettings.newBuilder()
                            .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                            .setHandleCodeInApp(true)
                            .setIOSBundleId("com.example.ios")
                            .setAndroidPackageName(
                                    "com.example.android",
                                    true,
                                    "12")
                            .build();
                }
            }
        });


    }
}