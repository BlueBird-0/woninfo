package com.bluebird.inhak.woninfo.Community.Textboard;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by InHak on 2017-12-31.
 */

public class test_delete extends Fragment {


    private Button sendbt;
    private EditText editdt;
    private EditText editdt2;
    private String Board;
    private String deletemessage;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.community_textboard, container, false);
        Log.d("test010", "DocumentSnapshot added with ID: ");

        sendbt = (Button)view.findViewById(R.id.test_delete);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Toast.makeText(getContext(), "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    Board = "대나무숲";
                    deletemessage = "0Dh3qtsBCJJxJZHe0k8O";
                    db.collection("Community").document("게시판").collection(Board).document(deletemessage)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("test010", "DocumentSnapshot added with ID: ");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("test010", "Error adding document", e);
                                }
                            });


                }
            }
        });
    return view;
    }
}