package com.bluebird.inhak.woninfo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserManager {
    static private FirebaseUser firebaseUser;
    static private FirebaseAuth auth;
    static private FirebaseAuth.AuthStateListener mAuthListener;

    //로그인 함수

    static public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password);
    }

    static public void logoutUser() {
        auth.signOut();
        //if (mAuthListener != null) {
        //    auth.removeAuthStateListener(mAuthListener);
        //}
    }


    //로그인 상태 확인
    static public boolean checkLoggedin()
    {
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
    static public Task<AuthResult> createUser(String email, String password)
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
                        }else {
                            Log.d("test001", "--------------회원가입 실패----------------");
                            Log.w("test001", task.getException());
                        }
                    }
                });
        return null;
    }

}
