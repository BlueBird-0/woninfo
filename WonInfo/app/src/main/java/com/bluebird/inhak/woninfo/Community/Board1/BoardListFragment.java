
package com.bluebird.inhak.woninfo.Community.Board1;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class BoardListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO 여기 String 으로 옮겨야함
    static int PAGE_COUNT = 10;  //한페이지에 보여주는 게시글 수
    static int PAGE_NUMBER = 0;     //현재 페이지 번호

    private BoardListAdapter boardListAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private ArrayList<BoardListItem> boardListItems = new ArrayList<>();
    private View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_list_fragment, container, false);
        setHasOptionsMenu(true);

        swipeRefresh = view.findViewById(R.id.community_layout_refrash);
        swipeRefresh.setOnRefreshListener(this);

        final long startTime = System.currentTimeMillis();   Log.d("comunity","측정시작");      //TODO 게시글 시간 측정
        //option count 가져오는 부분
        db.collection("Community").document("게시판").collection("대나무숲")
                .document("option").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        double option_count = task.getResult().getDouble("count");
                        Log.d("comunity", "총 게시글 수 : "+ option_count);

                        // 게시글 가져오는 부분
                        db.collection("Community").document("게시판").collection("대나무숲")
                                .limit(PAGE_COUNT)
                                .whereLessThan("num", option_count-(PAGE_COUNT*PAGE_NUMBER))
                                .orderBy("num", Query.Direction.DESCENDING)
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
                                                boardListItems.add(item);
                                            }
                                            long endTime = System.currentTimeMillis();   Log.d("comunity","측정끝");      //TODO 게시글 시간 측정
                                            Log.d("comunity", "게시글 불러오는 데 걸리는 시간 (ms):"+(endTime-startTime));
                                        } else {
                                            Log.w("comunity", "Error getting documents.", task.getException());
                                        }
                                        setRecyclerView();
                                    }
                                });
                    }
                });

                /*
        db.collection("Community").document("게시판").collection("대나무숲")
                .limit(PAGE_COUNT)
                .whereGreaterThanOrEqualTo("num", 5)
  //              .orderBy("num", Query.Direction.DESCENDING)
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
                                boardListItems.add(item);
                            }
                        } else {
                            Log.w("comunity", "Error getting documents.", task.getException());
                        }
                        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.community_recycler_list);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                        recyclerView.setLayoutManager(linearLayoutManager);

                        boardListAdapter = new BoardListAdapter(boardListItems,(MainActivity) getActivity());
                        recyclerView.setAdapter(boardListAdapter);
                        //setRecyclerView();
                    }
                });*/

        return view;
    }

    @Override
    public void onRefresh() {
        Log.d("comunity", "onRefresh");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                //loadData();
            }
        }, 2000);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar_menu_community_edit, menu);

        MenuItem editItem = menu.findItem(R.id.action_edit);
        editItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("test031","헿 여기 눌렀다능");
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new Textboard();
                loadFragment(fragment);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.community_recycler_list);
        //각 Item들이 RecyclerView 의 전체 크기를 변경하지 않는다면
        //setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        //변경될 가능성이 있다면 false로, 없다면 true를 설정해주세요
        recyclerView.setHasFixedSize(true);
        //RecyclerView에 Adapter를 설정해줍니다.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boardListAdapter = new BoardListAdapter(boardListItems,(MainActivity) getActivity());
        recyclerView.setAdapter(boardListAdapter);

        //setData();
    }

    /*
    private void setData(){
        items.clear();
        //RecyclerView 에 들어갈 데이터를 추가합니다.
        for(int i=0; i<titles.length; i++)
        {
            if( titles[i] != null ) {
                BoardListItem item = new BoardListItem(titles[i], contents[i], nums[i]);
                items.add(item);
                //데이터 추가가 완료되었으면 notifyDataSetChanged() 메서드를 호출해 데이터 변경 체크를 실시합니다.
                boardListAdapter.notifyDataSetChanged();
            }
        }
    }*/

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


