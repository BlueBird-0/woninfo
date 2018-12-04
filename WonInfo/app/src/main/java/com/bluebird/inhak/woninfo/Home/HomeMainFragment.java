package com.bluebird.inhak.woninfo.Home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bluebird.inhak.woninfo.Community.BoardListItem;


import com.bluebird.inhak.woninfo.Dictionary.A24Fragment.A24Fragment;

import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class HomeMainFragment extends Fragment {

    private NewsAdapter newsAdapter;
    private ArrayList<BoardListItem> newsItems = new ArrayList<>();
    AutoScrollViewPager autoViewPager;
    private SwipeRefreshLayout swipeRefresh;
    static Fragment fragment = null;
    private View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_main_fragment, container, false);
        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        data.add("http://www.wku.ac.kr/wp-content/themes/wku/common/img/main/www-20180727-ipsi-2.jpg");
        data.add("http://www.wku.ac.kr/wp-content/themes/wku/common/img/main/www-20181119-prime.jpg");
        data.add("http://www.wku.ac.kr/wp-content/themes/wku/common/img/main/www-20181022-autumn.jpg");
        data.add("http://www.wku.ac.kr/wp-content/themes/wku/common/img/main/www-20181029-aut_botonic_lib.jpg");

        autoViewPager = (AutoScrollViewPager) view.findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(getContext(), data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작


        ConstraintLayout topButton1 = (ConstraintLayout) view.findViewById(R.id.home_layout_smallbtn1);
        topButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PackageManager pm = getActivity().getPackageManager();
                    pm.getApplicationInfo("kr.co.libtech.sponge", PackageManager.GET_META_DATA);
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("kr.co.libtech.sponge");
                    startActivity(launchIntent);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=kr.co.libtech.sponge"));
                    startActivity(intent);
                }
            }
        });

        ConstraintLayout topButton2 = (ConstraintLayout) view.findViewById(R.id.home_layout_smallbtn2);
        topButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://elibrary.wku.ac.kr/lib/SlimaPlus.csp"));
                startActivity(intent);
            }
        });

        ConstraintLayout topButton3 = (ConstraintLayout) view.findViewById(R.id.home_layout_smallbtn3);
        topButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wvu.wku.ac.kr"));
                startActivity(intent);
            }
        });

        ConstraintLayout topButton4 = (ConstraintLayout)view.findViewById(R.id.home_layout_smallbtn4);
        topButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment bus = new A24Fragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container,bus)
                        .commit();

            }
        });

        setRecyclerView();
        onRefresh();
        return view;
    }

    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                newsAdapter = new NewsAdapter(newsItems,(MainActivity) getActivity());
                final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.Listview);
                //맨 처음에 값 넣어두기 위해 있음
                for(int i=0; i<3; i++) {
                    BoardListItem boardListItem = new BoardListItem();
                    boardListItem.setTitle("Loading...");
                    boardListItem.setBoard("최신글");
                    newsItems.add(boardListItem);
                }
                recyclerView.setAdapter(newsAdapter);

                db.collection("Community").document("게시판").collection("대나무숲")
                        .orderBy("num", Query.Direction.DESCENDING)
                        .limit(2)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task){
                                if(task.isSuccessful()){
                                    newsItems.remove(0);
                                    for(DocumentSnapshot document:task.getResult()){
                                        BoardListItem item = new BoardListItem();
                                        item.setDocumentId(document.getId());

                                        item.setBoard(String.valueOf(("대나무숲")));
                                        item.setTitle(document.get("title").toString());

                                        item.setId(document.get("id").toString());
                                        item.setContent(document.get("content").toString());
                                        item.setDate(document.get("date").toString());
                                        item.setLikeCount(document.getDouble("like_count"));
                                        item.setCommentCount(document.getDouble("comment_count"));
                                        newsItems.add(item);

                                        recyclerView.setAdapter(newsAdapter);
                                    }
                                }
                            }
                        });
                db.collection("Community").document("게시판").collection("자유게시판")
                        .orderBy("num", Query.Direction.DESCENDING)
                        .limit(2)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task){
                                if(task.isSuccessful()){
                                    newsItems.remove(0);
                                    for(DocumentSnapshot document:task.getResult()){
                                        BoardListItem item = new BoardListItem();
                                        item.setDocumentId(document.getId());

                                        item.setBoard(String.valueOf(("자유게시판")));
                                        item.setTitle(document.get("title").toString());
                                        item.setId(document.get("id").toString());
                                        item.setContent(document.get("content").toString());
                                        item.setDate(document.get("date").toString());
                                        item.setLikeCount(document.getDouble("like_count"));
                                        item.setCommentCount(document.getDouble("comment_count"));

                                        newsItems.add(item);

                                        recyclerView.setAdapter(newsAdapter);
                                    }
                                }
                            }
                        }); db.collection("Community").document("게시판").collection("자유시장")
                        .orderBy("num", Query.Direction.DESCENDING)
                        .limit(2




                        )
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task){
                                if(task.isSuccessful()){
                                    newsItems.remove(0);
                                    for(DocumentSnapshot document:task.getResult()){
                                        BoardListItem item = new BoardListItem();
                                        item.setDocumentId(document.getId());

                                        item.setBoard(String.valueOf(("자유시장")));
                                        item.setTitle(document.get("title").toString());

                                        item.setId(document.get("id").toString());
                                        item.setContent(document.get("content").toString());
                                        item.setDate(document.get("date").toString());
                                        item.setCommentCount(document.getDouble("comment_count"));


                                        newsItems.add(item);

                                        recyclerView.setAdapter(newsAdapter);
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void setRecyclerView(){
        ArrayList items = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.Listview);
        //각 Item들이 RecyclerView 의 전체 크기를 변경하지 않는다면
        //setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        //변경될 가능성이 있다면 false로, 없다면 true를 설정해주세요
        recyclerView.setHasFixedSize(false);
        //RecyclerView에 Adapter를 설정해줍니다.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //newsAdapter = new NewsAdapter(newsItems,(MainActivity) getActivity());
        newsAdapter = new NewsAdapter(items,(MainActivity) getActivity());

        recyclerView.setAdapter(newsAdapter);
        //recyclerView.setAdapter(newsAdapter2);

        recyclerView.setNestedScrollingEnabled(false);
        //setData();
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
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