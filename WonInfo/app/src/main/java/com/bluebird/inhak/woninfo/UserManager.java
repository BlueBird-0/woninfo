package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.File;
import java.io.IOException;

import static android.content.ContentValues.TAG;
import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class UserManager {
    static private FirebaseAuth auth;
    static private FirebaseUser firebaseUser;
    static private FirebaseAuth.AuthStateListener mAuthListener;




    //로그인 함수

    static public void loginUser(String email, String password) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LoginActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                }
                //로그인 후 사용자 정보창 새로고침
                ((MainActivity)mainContext).replaceNavigation();
            }
        };
        auth.signInWithEmailAndPassword(email,password);
        auth.addAuthStateListener(mAuthListener);
    }

    static public void logoutUser() {
        auth.signOut();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }

    }


    //로그인 상태 확인
    static public boolean checkLoggedin() {

        auth = FirebaseAuth.getInstance();

        firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null) {
            Log.d("test001", "로그인 안됨");


            return false;
        }
        else {
            Log.d("test001", "로그인 됨");
            return true;
        }

    }
    // 프로필 이미지 변경
    static public void updateProfile(){

    }

    //비밀번호 변경
    public void updatePassword(String newPassword){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updatePassword(newPassword)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override















                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.d("UserManager", "User password update.");
                    }
                }
            });
    }

    //회원가입
    static public Task<AuthResult> createUser(String email, String password, final String nickname)
    {
        Log.d("test001", "--------------회원가입 진입----------------");


        if(checkLoggedin() == true) {
            return null;
        }
        //이메일 체크

        //패스워드 체크

        //사용자에게 확인 메일 보내기
        /*
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Log.d("test001", "Email sent.");
                        }
                    }
                });
*/
        //회원가입
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("test001", "--------------회원가입 성공----------------");
                            FirebaseUser user = auth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nickname)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Log.d("test987", "User Profile updated.");
                                            }
                                        }
                                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("test987", "User Profile updateddddd.");

                                }
                            });
                        }else {
                            Log.d("test001", "--------------회원가입 실패----------------");
                            Log.w("test001", task.getException());
                        }
                    }
                });
        return null;
    }

}
