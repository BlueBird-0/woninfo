
package com.bluebird.inhak.woninfo.Community.Board1;


import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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


public class Textboard extends Fragment {
    private Button sendbt;
    private EditText editdt;
    private EditText editdt2;
    private String Board;
    private String date;
    private String option;
    private Double num;

    private ArrayList<Uri> imgUriList;
    private int imageCount;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.community_write2_fragment, container, false);
        setHasOptionsMenu(true);

        final LinearLayout imageButton = (LinearLayout)view.findViewById(R.id.write2_layout_picture);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getContext())
                        .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(ArrayList<Uri> uriList) {
                                // here is selected uri list
                                imgUriList = new ArrayList<>();
                                LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.write2_layout_picture);
                                linearLayout.removeAllViews();

                                imageCount = uriList.size();
                                for(Uri uri : uriList)
                                {
                                    imgUriList.add(uri);
                                    ImageView pictureImg= new ImageView(getContext());
                                    pictureImg.setBackground(null);

                                    Glide.with((getActivity()).getWindow().getDecorView().getRootView()).load(uri).into(pictureImg);
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

                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager());
            }
        });

        sendbt = (Button)view.findViewById(R.id.write2_button_confirm);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Toast.makeText(getContext(), "글이 작성되었습니다.", Toast.LENGTH_SHORT).show();
                    option = "option";

                    DocumentReference Count1 = db.collection("Community").document("게시판").collection("대나무숲").document(option);
                    Count1.get().addOnCompleteListener(
                            new OnCompleteListener<DocumentSnapshot>(){
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        final DocumentSnapshot document = task.getResult();

                                        if (document.exists()) {
                                            num = document.getDouble("count")+1;

                                            GregorianCalendar calendar = new GregorianCalendar();
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM\nhh:mm");
                                            String now = dateFormat.format(calendar.getTime());
                                            Log.d("community", "글 작성 시간 : "+now);
                                            //db에 insert시켜준다

                                            editdt = (EditText) view.findViewById(R.id.write2_edit_title);
                                            editdt2 = (EditText) view.findViewById(R.id.write2_edit_content);
                                            date = now;


                                            String msg = editdt.getText().toString();
                                            String msg2 = editdt2.getText().toString();
                                            Map<String, Object> newArticle = new HashMap<>();

                                            newArticle.put("title",editdt.getText().toString());
                                            newArticle.put("content",editdt2.getText().toString());
                                            newArticle.put("date",date);
                                            newArticle.put("num", num);

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            newArticle.put("id", user.getDisplayName());
                                            newArticle.put("uid", user.getUid());
                                            newArticle.put("profile", user.getPhotoUrl().toString());

                                            newArticle.put("comment_count", 0);
                                            newArticle.put("like_count", 0);
                                            //이미지 카운트
                                            newArticle.put("image_count", imageCount);

                                            Board = "대나무숲";
                                            Log.d("test050", "문서명 : "+ document.getId());
                                            CollectionReference collectionReference = db.collection("Community").document("게시판").collection(Board);
                                                    collectionReference.add(newArticle)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            if(imgUriList.size() != 0)
                                                                saveStoreImages(imgUriList,documentReference.getId());
                                                            Log.d("test050", documentReference.getId());
                                                        }
                                                    });

                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                    });
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();

            }
        });
        return view;
    }

    public void saveStoreImages(List<Uri> uriList, String documentId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        int i=0;
        for(Uri uri : uriList) {
            StorageReference riversRef = storageReference.child("board1/" + documentId+"_image_"+i);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private boolean loadFragment(Fragment fragment)
    {
        //switching fragment
        if(fragment != null)
        {
            getFragmentManager().beginTransaction().addToBackStack(null);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
