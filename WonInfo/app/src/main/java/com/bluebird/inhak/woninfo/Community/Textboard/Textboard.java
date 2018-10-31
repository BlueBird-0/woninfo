package com.bluebird.inhak.woninfo.Community.Textboard5;


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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by InHak on 2017-12-31.
 */

public class Textboard extends Fragment {


    private Button sendbt;
    private EditText editdt;
    private EditText editdt2;
    private String Board;
    private String date;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.community_textboard, container, false);


        sendbt = (Button)view.findViewById(R.id.textboard_write_btn);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                {
                    Toast.makeText(getContext(), "글이 작성되었습니다.", Toast.LENGTH_SHORT).show();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    GregorianCalendar calendar = new GregorianCalendar();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int min = calendar.get(Calendar.MINUTE);
                    int sec = calendar.get(Calendar.SECOND);
                    //변수에 각각의 값을 담아주고
                    String now = year + "-" + month + "-" + day + "\n" + hour + ":" + min + ":" + sec;


                    //db에 insert시켜준다

                    editdt = (EditText) view.findViewById(R.id.editText2);
                    editdt2 = (EditText) view.findViewById(R.id.community_market_editText4);
                    date = now;

                    String msg = editdt.getText().toString();
                    String msg2 = editdt2.getText().toString();
                    Map<String, String> 대나무숲 = new HashMap<>();

                    Map<String, String> uData = new HashMap<>();
                    //uData.put("title", msg);
                    //uData.put("content", msg2);
                    //uData.put("date", now);
                    대나무숲.put("title",editdt.getText().toString());
                    대나무숲.put("content",editdt2.getText().toString());
                    대나무숲.put("date",date);


                    Board = "대나무숲";


                    //databaseReference.child("message").push().setValue(msg);
                    //// Add a new document with a generated ID
                    db.collection("Community").document("게시판").collection(Board)
                            .add(대나무숲)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("test002", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("test002", "Error adding document", e);
                                }
                            });
                    Log.d("test004", "test : \n");
                }
            }
        });






        return view;
    }
}
