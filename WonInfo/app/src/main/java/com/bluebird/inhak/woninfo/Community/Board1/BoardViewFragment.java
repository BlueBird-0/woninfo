
package com.bluebird.inhak.woninfo.Community.Board1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BoardViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO 여기 String 으로 옮겨야함
    //static int PAGE_COUNT = 1;  //한페이지에 보여주는 게시글 수


    static  private String titles;
    static  private String contents;
    static  private String dates;
    static private FirebaseAuth auth;
    static private FirebaseUser firebaseUser;
    static private FirebaseAuth.AuthStateListener mAuthListener;


    private BoardListAdapter boardListAdapter;
    private String Board;
    private ArrayList<BoardListItem> items = new ArrayList<>();
    private View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView editdt;
    private TextView editdt2;
    private TextView editdt3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_item_document, container, false);
        setHasOptionsMenu(true);

        SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.community_board1_layout);
        swipeRefresh.setOnRefreshListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getDisplayName();
        Double num = getArguments().getDouble("Bundle_num");


        Board="대나무숲";

        db.collection("Community").document("게시판").collection(Board)
                .whereEqualTo("num", num)   //////////////////// 여기 시발놈
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                ImageView imageView = (ImageView)view.findViewById(R.id.community_board1_profile);


                                Log.d("test040", user.getPhotoUrl().toString());


                            if(user.getPhotoUrl() != null)
                                Glide.with(getActivity()).load(user.getPhotoUrl()).into(imageView);


                                Log.d("test123", "여기 실행");
                                titles = document.get("title").toString();
                                contents = document.get("content").toString();
                                dates = document.get("date").toString();



                                editdt =  (TextView) view.findViewById(R.id.community_board1_title);
                                editdt2 = (TextView) view.findViewById(R.id.community_board1_content);
                                editdt3 = (TextView) view.findViewById(R.id.community_board1_date);
                                //editdt4 = (TextView) view.findViewById(R.id.community_board1_nickname);

                                Log.d("test100", titles);

                                editdt.setText(titles);
                                Log.d("test015", "여기 실행 되나요?");
                                editdt2.setText(contents);
                                editdt3.setText(dates);
                            }
                        }
                    }
                });
        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}

