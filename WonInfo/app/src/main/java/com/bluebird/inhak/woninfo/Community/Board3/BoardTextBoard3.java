
package com.bluebird.inhak.woninfo.Community.Board3;


import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;

import static android.support.constraint.Constraints.TAG;
import static android.view.View.getDefaultSize;
import static com.bluebird.inhak.woninfo.MainActivity.mainContext;


/**
 * Created by InHak on 2017-12-31.
 */


public class BoardTextBoard3 extends AppCompatActivity {
    private Button sendbt;
    private EditText editdt;
    private EditText editdt2;
    private EditText editdt3;
    private EditText editdt4;
    private String date;
    private String option;
    private Double num;

    private ArrayList<Uri> imgUriList;
    private int imageCount;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_write3_fragment);
        imgUriList = new ArrayList<>();

        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.write2_layout_picture);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        // 권한 있을 때  사진 넣기
                        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getApplicationContext())
                                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                                    @Override
                                    public void onImagesSelected(ArrayList<Uri> uriList) {
                                        // here is selected uri list
//                                        LinearLayout constraintLayout= (LinearLayout) findViewById(R.id.write2_layout_picture);
                                        linearLayout.removeAllViews();

                                        imageCount = uriList.size();
                                        for(Uri uri : uriList)
                                        {
                                            imgUriList.add(uri);
                                            ImageView pictureImg= new ImageView(getApplicationContext());
                                            pictureImg.setBackground(null);

                                            Glide.with(getWindow().getDecorView().getRootView()).load(uri).into(pictureImg);
                                            linearLayout.addView(pictureImg);

                                            LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams) pictureImg.getLayoutParams();

                                            loparams.weight = 0;
                                            loparams.rightMargin = 14;
                                            if(pictureImg.getWidth() > linearLayout.getHeight())
                                                loparams.width = linearLayout.getHeight();
                                            loparams.height = linearLayout.getHeight();
                                            pictureImg.setLayoutParams(loparams);
                                        }
                                    }
                                })
                                .setPeekHeight(1600)
                                .showTitle(false)
                                .setCompleteButtonText("OK")
                                .setEmptySelectionText("No Select")
                                .create();

                        bottomSheetDialogFragment.show(getSupportFragmentManager());
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
        });

        sendbt = (Button)findViewById(R.id.write2_button_confirm);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    editdt2 = (EditText) findViewById(R.id.write2_edit_content);
                    editdt3 = (EditText) findViewById(R.id.write2_edit_price);
                    editdt4 = (EditText) findViewById(R.id.write2_edit_kind);
                    String errorString = "";
                    if (  editdt2.getText().toString().equals("") ||
                            editdt3.getText().toString().equals("") ||
                            editdt4.getText().toString().equals("")) {
                        errorString = "빈칸을 입력하세요.";
                    }
                    if(!errorString.equals("")) {
                        View main_view = (View) findViewById(R.id.snackbar_view);
                        Snackbar snackbar = Snackbar.make(main_view, errorString, Snackbar.LENGTH_SHORT);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext, R.color.Theme_Blue));
                        snackbar.show();
                        return;
                    }



                    Toast.makeText(getApplicationContext(), "글이 작성되었습니다.", Toast.LENGTH_SHORT).show();

                    option = "option";

                    DocumentReference Count1 = db.collection("Community").document("게시판").collection("자유시장").document(option);
                    Count1.get().addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        final DocumentSnapshot document = task.getResult();

                                        if (document.exists()) {
                                            num = document.getDouble("count") + 1;

                                            GregorianCalendar calendar = new GregorianCalendar();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nhh:mm");
                                            String now = dateFormat.format(calendar.getTime());
                                            Log.d("community", "글 작성 시간 : " + now);
                                            //db에 insert시켜준다

                                            editdt2 = (EditText) findViewById(R.id.write2_edit_content);
                                            editdt3 = (EditText) findViewById(R.id.write2_edit_price);
                                            editdt4 = (EditText) findViewById(R.id.write2_edit_kind);

                                            date = now;

                                            String msg2 = editdt2.getText().toString();
                                            String msg3 = editdt3.getText().toString();
                                            String msg4 = editdt4.getText().toString();
                                            Map<String, Object> newArticle = new HashMap<>();

                                            newArticle.put("title", msg4 + " 판매글 " + msg3 + "￦");
                                            newArticle.put("content", msg2);
                                            newArticle.put("price", msg3);
                                            newArticle.put("kinds", msg4);

                                            newArticle.put("date", date);
                                            newArticle.put("num", num);

                                            newArticle.put("id", UserManager.firebaseUser.getDisplayName());
                                            newArticle.put("uid", UserManager.firebaseUser.getUid());

                                            newArticle.put("comment_count", 0);
                                            // newArticle.put("like_count", 0);
                                            //이미지 카운트
                                            newArticle.put("image_count", imageCount);

                                            Log.d("test050", "문서명 : " + document.getId());
                                            CollectionReference collectionReference = db.collection("Community").document("게시판").collection("자유시장");
                                            collectionReference.add(newArticle)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            if (imgUriList.size() != 0) {
                                                                saveStoreImages(imgUriList, documentReference.getId());
                                                            }
                                                        }
                                                    });

                                            db.collection("Community").document("게시판").collection("자유시장").document("option")
                                                    .update("count", num);

                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });
                }
                finish();
            }
        });
    }


    public void saveStoreImages(List<Uri> uriList, String documentId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        int i=0;
        for(Uri uri : uriList) {
            StorageReference riversRef = storageReference.child("board3/" + documentId+"_image_"+i);
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
                    Log.d("test098", "success");
                }
            });
            i++;
        }
    }
}
