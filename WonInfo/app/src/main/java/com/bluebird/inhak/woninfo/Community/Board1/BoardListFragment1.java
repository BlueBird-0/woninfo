
package com.bluebird.inhak.woninfo.Community.Board1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BoardListFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO 여기 String 으로 옮겨야함
    static double BOARD_COUNT = 20; //한페이지에 보여주는 게시글 수
    static double PAGE_NUMBER = 0;  //현재 페이지 번호
    static double PAGE_COUNT = 5;   //페이지 번호
    static double PAGE_ALL_COUNT;   //총 페이지 번호

    private BoardListAdapter1 boardListAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private ArrayList<BoardListItem> boardListItems = new ArrayList<>();
    private View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_list_fragment, container, false);
        setHasOptionsMenu(true);

        swipeRefresh = view.findViewById(R.id.community_layout_refrash);
        swipeRefresh.setOnRefreshListener(this);


        //실행시 새로고침 실행
        this.onRefresh();
        return view;
    }

    @Override
    public void onRefresh() {
        Log.d("comunity", "onRefresh");
        swipeRefresh.setRefreshing(true);
        boardListItems.clear(); //원래 리스트 삭제
        setRecyclerView();

        final long startTime = System.currentTimeMillis();   Log.d("comunity","측정시작");      //TODO 게시글 시간 측정
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                db.collection("Community").document("게시판").collection("대나무숲")
                        .limit((long)(BOARD_COUNT*(PAGE_NUMBER+1)))
                        .orderBy("num", Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int index = 0;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        if(index >= BOARD_COUNT*(PAGE_NUMBER) && index < BOARD_COUNT*(PAGE_NUMBER+1))
                                        {
                                            BoardListItem item = new BoardListItem();
                                            item.setDocumentId(document.getId());
                                            item.setTitle(document.get("title").toString());
                                            item.setContent(document.get("content").toString());
                                            item.setNum(document.getDouble("num"));
                                            item.setId(document.getString("id"));
                                            item.setUid(document.getString("uid"));
                                            item.setDate(document.getString("date"));
                                            item.setCommentCount(document.getDouble("comment_count"));
                                            item.setLikeCount(document.getDouble("like_count"));
                                            item.setImageCount(document.getDouble("image_count"));

                                            boardListItems.add(item);
                                        }
                                        index++;
                                    }
                                    long endTime = System.currentTimeMillis();   Log.d("comunity","측정끝");      //TODO 게시글 시간 측정
                                    Log.d("comunity", "게시글 불러오는 데 걸리는 시간 (ms):"+(endTime-startTime));
                                } else {
                                    Log.w("comunity", "Error getting documents.", task.getException());
                                }
                                setRecyclerView();
                                swipeRefresh.setRefreshing(false);
                                Log.d("comunity", "새로고침 완료");
                            }
                        });

                setPagerLayout();

            }
        });
    }


    public void setPagerLayout()
    {
        //paging
        db.collection("Community").document("게시판").collection("대나무숲").document("option")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("test040", "총페이지 수 :"+documentSnapshot.get("count").toString());
                        PAGE_ALL_COUNT = (int)((documentSnapshot.getDouble("count")-1  )/ BOARD_COUNT);
                        final LinearLayout pageList = view.findViewById(R.id.board1_list_page);
                        final TextView[] texts = new TextView[5];
                        texts[0] = (TextView)view.findViewById(R.id.board1_text_page1);
                        texts[1] = (TextView)view.findViewById(R.id.board1_text_page2);
                        texts[2] = (TextView)view.findViewById(R.id.board1_text_page3);
                        texts[3] = (TextView)view.findViewById(R.id.board1_text_page4);
                        texts[4] = (TextView)view.findViewById(R.id.board1_text_page5);

                        int []pageNum = new int[5];
                        pageNum[0] = (int)(PAGE_NUMBER % PAGE_COUNT -2);
                        pageNum[1] = (int)(PAGE_NUMBER % PAGE_COUNT -1);
                        pageNum[2] = (int)(PAGE_NUMBER % PAGE_COUNT );
                        pageNum[3] = (int)(PAGE_NUMBER % PAGE_COUNT +1);
                        pageNum[4] = (int)(PAGE_NUMBER % PAGE_COUNT +2);

                        if(pageNum[0] < 0)
                        {
                            pageList.removeView(texts[0]);
                            pageList.addView(texts[0]);
                            pageNum[0] += PAGE_COUNT;
                        }
                        if(pageNum[1]  < 0)
                        {
                            pageList.removeView(texts[1]);
                            pageList.addView(texts[1]);
                            pageNum[1] += PAGE_COUNT;
                        }
                        pageList.removeAllViews();
                        for(int i=0; i<5; i++)
                        {
                            pageList.addView(texts[i]);
                        }

                        for(int i=0; i<5; i++)
                        {
                            if(pageNum[i] == PAGE_NUMBER) {
                                texts[i].setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
                            }
                            texts[i].setText(String.valueOf(pageNum[i]+1));
                            texts[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    PAGE_NUMBER = Double.parseDouble(((TextView)v).getText().toString())-1;
                                    onRefresh();
                                }
                            });
                            if(pageNum[i] > PAGE_ALL_COUNT)
                                texts[i].setVisibility(View.INVISIBLE);
                            else
                                texts[i].setVisibility(View.VISIBLE);
                        }

                        //뒤로가기
                        TextView textPrev = (TextView)view.findViewById(R.id.board1_button_prev);
                        textPrev.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PAGE_NUMBER -= PAGE_COUNT;
                                if(PAGE_NUMBER < 0)
                                    PAGE_NUMBER = 0;
                                onRefresh();
                            }
                        });
                        //앞으로가기
                        TextView nextPrev = (TextView)view.findViewById(R.id.board1_button_next);
                        nextPrev.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PAGE_NUMBER += PAGE_COUNT;
                                if(PAGE_NUMBER > PAGE_ALL_COUNT)
                                    PAGE_NUMBER = PAGE_ALL_COUNT;
                                onRefresh();
                            }
                        });
                    }
                });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.actionbar_menu_community_edit, menu);

        MenuItem editItem = menu.findItem(R.id.action_edit);
        editItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent=new Intent(getActivity(), BoardTextBoard1.class);
                getActivity().startActivity(intent);
                return false;
            }
        });
    }

    private void setRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.community_recycler_list);
        //각 Item들이 RecyclerView 의 전체 크기를 변경하지 않는다면
        //setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        //변경될 가능성이 있다면 false로, 없다면 true를 설정해주세요
        recyclerView.setHasFixedSize(true);
        //RecyclerView에 Adapter를 설정해줍니다.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boardListAdapter = new BoardListAdapter1(boardListItems,(MainActivity) getActivity());
        recyclerView.setAdapter(boardListAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        //setData();
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


