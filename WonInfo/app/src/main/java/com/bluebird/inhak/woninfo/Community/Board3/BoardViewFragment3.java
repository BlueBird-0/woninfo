
package com.bluebird.inhak.woninfo.Community.Board3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.Board1.BoardListAdapter;
import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BoardViewFragment3 extends Fragment{
    // TODO 여기 String 으로 옮겨야함
    //static int PAGE_COUNT = 1;  //한페이지에 보여주는 게시글 수
    int imsi_num = 1;

    static  private String titles;
    static  private String contents;
    static  private String dates;






    private BoardListAdapter boardListAdapter;
    private String Board3;
    private ArrayList<BoardListItem> items = new ArrayList<>();
    private View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView editdt;
    private TextView editdt2;
    private TextView editdt3;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_text_comments_profile, container, false);

        Board3="자유시장";

        db.collection("Community").document("게시판").collection(Board3)
                .whereEqualTo("num", imsi_num)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                titles = document.get("title").toString();
                                contents = document.get("content").toString();
                                dates = document.get("date").toString();

                                editdt =  (TextView) view.findViewById(R.id.community_board_title);
                                editdt2 = (TextView) view.findViewById(R.id.community_board_content);
                                editdt3 = (TextView) view.findViewById(R.id.community_board_date);

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

}
