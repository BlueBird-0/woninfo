
package com.bluebird.inhak.woninfo.Community.Board3;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardListFragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO 여기 String 으로 옮겨야함
    static int PAGE_COUNT = 9;  //한페이지에 보여주는 게시글 수
    static int PAGE_NUMBER = 0;     //현재 페이지 번호

    private BoardListAdapter3 boardListAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private ArrayList<BoardListItem> boardListItems = new ArrayList<>();
    private View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_market_list, container, false);




        setHasOptionsMenu(true);

        swipeRefresh = view.findViewById(R.id.community_layout_refrash);
        swipeRefresh.setOnRefreshListener(this);

        //실행시 새로고침 실행
        swipeRefresh.setRefreshing(true);
        this.onRefresh();
        //option count 가져오는 부분

        /*
        db.collection("Community").document("게시판").collection("대나무숲")
                .document("option").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        final double option_count = task.getResult().getDouble("count");
                        Log.d("comunity", "총 게시글 수 : "+ option_count);
*/
        // 게시글 가져오는 부분
        /*
                        db.collection("Community").document("게시판").collection("대나무숲")
                                .limit(PAGE_COUNT * (PAGE_NUMBER*PAGE_COUNT))
                                .orderBy("num", Query.Direction.DESCENDING)
                                //.whereLessThan("num", option_count-(PAGE_COUNT*PAGE_NUMBER))
                                //.whereLessThan("num", PAGE_COUNT)
                                .get()

                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot document : task.getResult()) {
                                                //Map<String,Object> map = document.getData();
                                                String title = document.get("title").toString();
                                                String content = document.get("content").toString();
                                                Double num = document.getDouble("num");

                                                BoardListItem item = new BoardListItem(title, content, num);
                                                item.setId(document.getString("id"));
                                                item.setUid(document.getString("uid"));
                                                item.setDate(document.getString("date"));
                                                item.setCommentCount(document.getDouble("comment_count"));
                                                item.setLikeCount(document.getDouble("like_count"));
                                                item.setImageCount(document.getDouble("image_count"));
                                                boardListItems.add(item);
                                            }
                                            long endTime = System.currentTimeMillis();   Log.d("comunity","측정끝");      //TODO 게시글 시간 측정
                                            Log.d("comunity", "게시글 불러오는 데 걸리는 시간 (ms):"+(endTime-startTime));
                                        } else {
                                            Log.w("comunity", "Error getting documents.", task.getException());
                                        }
                                        setRecyclerView();
                                    }
                                });*/
                        /*
                    }
                });
                */

        return view;
    }

    @Override
    public void onRefresh() {
        Log.d("comunity", "onRefresh");
        final long startTime = System.currentTimeMillis();   Log.d("comunity","측정시작");      //TODO 게시글 시간 측정
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                db.collection("Community").document("게시판").collection("자유시장")
                        //.limit(PAGE_COUNT)
                        .limit(PAGE_COUNT*(PAGE_NUMBER+1))
                        .orderBy("num", Query.Direction.DESCENDING)
                        //.whereLessThan("num", option_count-(PAGE_COUNT*PAGE_NUMBER))
                        //.whereLessThan("num", PAGE_COUNT)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boardListItems.clear(); //원래 리스트 삭제

                                    int index = 0;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        BoardListItem item = new BoardListItem();
                                        item.setDocumentId(document.getId());
                                        item.setTitle(document.get("title").toString());
                                        item.setPrice(document.get("price").toString());
                                        item.setKinds(document.get("kinds").toString());
                                        item.setTitle(document.get("content").toString());
                                        item.setNum(document.getDouble("num"));

                                        item.setId(document.getString("id"));
                                        item.setUid(document.getString("uid"));
                                        item.setDate(document.getString("date"));
                                        item.setCommentCount(document.getDouble("comment_count"));
                                        //item.setLikeCount(document.getDouble("like_count"));
                                        item.setImageCount(document.getDouble("image_count"));

                                        boardListItems.add(item);
                                        if( index++ > PAGE_COUNT) break;
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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new Textboard3();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_open, 0, 0, R.anim.slide_close);
                fragmentTransaction.add(R.id.main_fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return false;
            }
        });
    }

    private void setRecyclerView(){

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.community_recycler_market_list);
        //각 Item들이 RecyclerView 의 전체 크기를 변경하지 않는다면
        //setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        //변경될 가능성이 있다면 false로, 없다면 true를 설정해주세요

        int count = 3;
        //recyclerView.setLayoutManager(LayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), count));
        recyclerView.setHasFixedSize(true);
        //RecyclerView에 Adapter를 설정해줍니다.
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),count));
        boardListAdapter = new BoardListAdapter3(boardListItems,(MainActivity) getActivity());
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


