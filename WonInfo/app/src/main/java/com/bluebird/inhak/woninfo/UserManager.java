package com.bluebird.inhak.woninfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedonactivityresult.TedOnActivityResult;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class UserManager {
    static private FirebaseAuth auth;
    static private FirebaseUser firebaseUser;
    static private FirebaseAuth.AuthStateListener mAuthListener;
    static private FirebaseStorage storage;
    static private NavigationView navigationView;



    //프로필사진 선택 및 크롭
    static public void profielPicSelect(final FragmentManager fragmentManager) {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 있을 때
                TedBottomPicker bottomPicker = new TedBottomPicker.Builder(MainActivity.mainContext)
                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {

                                //uri 활용

                                CropImage.activity(uri).setAspectRatio(1,1)
                                        .setRequestedSize(300, 300)
                                        .start((Activity) mainContext);


                            }
                        })
                      .create();
                bottomPicker.show(fragmentManager);
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.mainContext, "권한 없음\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(mainContext.getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("사진첩 접근 권한이 필요합니다.")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    static public void profilePicDelete(){
        // Create a storage reference from our app
        // StorageReference storageRef = storage.getReference();
        // Create a reference to the file to delete
        //StorageReference desertRef = storageRef.child("images/desert.jpeg");
        // Delete the file
        storage.getReferenceFromUrl(firebaseUser.getPhotoUrl().toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("test031","사진삭제 완료");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("test031","사진삭제 실패");
            }
        });
    }

    //프로필사진 firebase 업로드 및 업데이트
    static  public void profilePicUpdate(Uri uri){
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference riversRef = storageReference.child("images/"+uri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // unsuccessful upload
                Log.d("test098", "unsuccess");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final  FirebaseUser user = auth.getCurrentUser();

                final ImageView profilePic = (ImageView)((Activity)mainContext).getWindow().getDecorView().getRootView().findViewById(R.id.nav_btn_profilepic);
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("test098", downloadUrl.toString());

                profilePicDelete();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(downloadUrl)
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Glide.with(((Activity)mainContext)
                                            .getWindow().getDecorView().getRootView())
                                            .load(user.getPhotoUrl())
                                            .into(profilePic);

                                    Log.d("test098",user.getPhotoUrl().toString());
                                }
                            }
                        });

                //Log.d("test098", user.getPhotoUrl().toString());


            }
        });
    }


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

    //로그아웃
    static public void logoutUser() {
        auth.signOut();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
        ((MainActivity)mainContext).replaceNavigation();
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


                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Log.d("test031", "Email sent.");
                                            }
                                            else{
                                                Log.d("test031","Email failed.");
                                            }
                                        }
                                    });

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
