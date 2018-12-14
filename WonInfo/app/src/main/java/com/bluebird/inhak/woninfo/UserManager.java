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
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.bluebird.inhak.woninfo.MainActivity.mainContext;
import static com.bluebird.inhak.woninfo.WebViewFragment.view;

public class UserManager {
    static public FirebaseAuth auth;
    static public FirebaseUser firebaseUser;
    static private FirebaseAuth.AuthStateListener mAuthListener;
    static private FirebaseStorage storage;
    static private NavigationView navigationView;


    //프로필사진 선택 및 크롭
    static public void profilePicSelect(final FragmentManager fragmentManager) {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 있을 때
                TedBottomPicker bottomPicker = new TedBottomPicker.Builder(MainActivity.mainContext)
                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {

                                //uri 활용
                                String userUid = firebaseUser.getUid();

                                CropImage.activity(uri).setActivityTitle("이미지 자르기")
                                        .setAspectRatio(1,1)
                                        .setRequestedSize(256,256)
                                        .start((Activity)mainContext);
/*
                                CropImage.activity(uri).setAspectRatio(1,1)
                                        .setRequestedSize(300, 300)
                                        .start((Activity) mainContext);
*/
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
        StorageReference riversRef = storageReference.child("profiles/"+firebaseUser.getUid()+"_profile");
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
                profilePic.setImageResource(R.drawable.ic_profile);
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("test098", downloadUrl.toString());
                //profilePicDelete();
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
        View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
        Snackbar snackbar = Snackbar.make(main_view, "로그아웃 성공", Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
        snackbar.show();
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
    static public Task<AuthResult> createUser(String email, String password, final String nickname) {
        Log.d("test001", "--------------회원가입 진입----------------");
        //이메일 체크

        //패스워드 체크

        //회원가입

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("test001", "--------------회원가입 성공----------------");
                            View main_view = (View) view.getRootView().findViewById(R.id.snackbar_view);
                            Snackbar snackbar = Snackbar.make(main_view, "회원가입 성공", Snackbar.LENGTH_SHORT);
                            View snackBarView = snackbar.getView();
                            snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext, R.color.Theme_Blue));
                            snackbar.show();
                            final FirebaseUser user = auth.getCurrentUser();


                            //TODO 이메일 인증 메일은 보내지만 인증은 안하고 자동으로 로그인 되는중...ㅅㅂ
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("test031", "Email sent.");
                                            } else {
                                                Log.d("test031", "Email failed.");
                                            }
                                        }
                                    });

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nickname)
                                    .build();

                            Log.d("test001","회원가입");

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                ((MainActivity)mainContext).replaceNavigation();

                                                /*  사용자 Database 제작
                                                    Firestore > Users
                                                */
                                                Map<String, Object> newArticle = new HashMap<>();
                                                newArticle.put("name", user.getDisplayName());
                                                newArticle.put("userId", user.getUid());
                                                newArticle.put("message_token", null);

                                                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                db.collection("Users").document( user.getUid() )
                                                        .set(newArticle)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Map<String, Object> readMeArticle = new HashMap<>();
                                                                readMeArticle.put("desc", "anon, boardName, date, lastMessage, messageRoomUid, nickname, uid");
                                                                db.collection("Users").document(user.getUid()).collection("MessageJoin")
                                                                        .document("README").set(readMeArticle);

                                                                new MyFirebaseInstanceIDService().onTokenRefresh();
                                                            }
                                                        });
                                            }
                                        }
                                    });

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("test001", "--------------회원가입 실패----------------");
                        View main_view = (View)view.getRootView().findViewById(R.id.snackbar_view);
                        Snackbar snackbar = Snackbar.make(main_view,"회원가입 실패\n"+e.getMessage(), Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                        snackbar.show();
                    }
                });
        return null;
    }

}
