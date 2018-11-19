
package com.bluebird.inhak.woninfo.Community.Board3;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;


/**
 * Created by InHak on 2017-12-31.
 */


public class Textboard3 extends Fragment {
    private Button sendbt;
    private EditText editdt;
    private EditText editdt2;
    private EditText editdt3;
    private EditText editdt4;
    private String Board;
    private String date;
    private String option;
    private Double nums;



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.community_market_textbroad, container, false);
        setHasOptionsMenu(true);

        sendbt = (Button)view.findViewById(R.id.market_textboard_write_btn);
        sendbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Toast.makeText(getContext(), "글이 작성되었습니다.", Toast.LENGTH_SHORT).show();
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Board = "자유시장";
                    option = "option";


                    DocumentReference Count1 = db.collection("Community").document("게시판").collection(Board).document(option);
                    Count1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();

                                if (document.exists()) {
                                    Log.d("test452", "DocumentSnapshot data: " + document.getData());
                                    nums = document.getDouble("count");

                                    GregorianCalendar calendar = new GregorianCalendar();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM\nhh:mm");
                                    String now = dateFormat.format(calendar.getTime());
                                    Log.d("community", "글 작성 시간 : "+now);


                                    //db에 insert시켜준다

                                    editdt = (EditText) view.findViewById(R.id.write2_edit_titles);
                                    editdt2 = (EditText) view.findViewById(R.id.write2_edit_kind);
                                    editdt3 = (EditText) view.findViewById(R.id.write2_edit_price);
                                    editdt4 = (EditText) view.findViewById(R.id.write2_edit_contents);

                                    date = now;


                                    String msg = editdt.getText().toString();
                                    String msg2 = editdt2.getText().toString();
                                    String msg3 = editdt3.getText().toString();
                                    String msg4 = editdt4.getText().toString();
                                    Map<String, Object> 자유시장 = new HashMap<>();

                                    자유시장.put("title",editdt.getText().toString());
                                    자유시장.put("kinds",editdt2.getText().toString());
                                    자유시장.put("price",editdt3.getText().toString());
                                    자유시장.put("content",editdt4.getText().toString());
                                    자유시장.put("date",date);
                                    자유시장.put("num", nums);


                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    자유시장.put("id", user.getDisplayName());
                                    자유시장.put("uid", user.getUid());
                                    자유시장.put("profile", user.getPhotoUrl().toString());

                                    자유시장.put("comment_count", 0);
                                    //자유시장.put("like_count", 0);
                                    //이미지 카운트
                                    자유시장.put("image_count", 0);


                                    CollectionReference collectionReference = db.collection("Community").document("게시판").collection(Board);
                                    collectionReference.add(자유시장)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("test002", "DocumentSnapshot added with ID: " + documentReference.getId());
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


                    final DocumentReference Count = db.collection("Community").document("게시판").collection(Board).document(option);
                    db.runTransaction(new Transaction.Function<Void>() {
                        @Override
                        public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                            DocumentSnapshot snapshot = transaction.get(Count);
                            double newcheckpoint = snapshot.getDouble("count") + 1;
                            transaction.update(Count, "count", newcheckpoint);
                            // Success
                            return null;
                        }
                    });
                }

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment3();
                loadFragment(fragment);

            }
        });






        return view;
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
