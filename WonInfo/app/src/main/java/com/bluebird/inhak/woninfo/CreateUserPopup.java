package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class CreateUserPopup extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_user_activity);

        final EditText email = ((EditText)findViewById(R.id.create_user_email_edit));
        final EditText password = ((EditText)findViewById(R.id.create_user_password_edit));
        final EditText password_confirm = ((EditText)findViewById(R.id.create_user_confirm_edit));
        final EditText nickname = ((EditText)findViewById(R.id.create_user_id_edit));
        Button createBtn = (Button)findViewById(R.id.create_user_create_btn);


        createBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //비밀번호 확인
                if (!password.getText().toString().equals(password_confirm.getText().toString())) {
                    View main_view = (View)findViewById(R.id.create_layout_main);
                    Snackbar snackbar = Snackbar.make(main_view, "비밀번호를 확인해주세요", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext, R.color.Theme_Blue));
                    snackbar.show();
                    return;
                }

                if (password_confirm.getText().toString().equals("")) {
                    password_confirm.requestFocus();
                    return;
                }
                if (password.getText().toString().equals("")) {
                    password.requestFocus();
                    return;
                }
                if (email.getText().toString().equals("")) {
                    email.requestFocus();
                    return;
                }
                if (nickname.getText().toString().equals("")) {
                    nickname.requestFocus();
                    return;
                }

                UserManager.createUser(email.getText().toString(), password.getText().toString(), nickname.getText().toString());
                Log.d("test001", "asdsd");

                    // 아이디와 패스워드가 같은지 확인   GUI 수정
                    /*
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
                    */
                    finish();
            }
        });

    }
}