
package com.bluebird.inhak.woninfo.Community.Board3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluebird.inhak.woninfo.Community.Board1.BoardListAdapter;
import com.bluebird.inhak.woninfo.Community.Board2.Textboard2;
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

public class BoardListFragment3 extends Fragment{
    // TODO 여기 String 으로 옮겨야함
    static int PAGE_COUNT = 9;  //한페이지에 보여주는 게시글 수

    static  private String[] titles = new String[PAGE_COUNT];
    static  private String[] contents = new String[PAGE_COUNT];
    static  private int[] nums = new int[PAGE_COUNT];

    private BoardListAdapter3 boardListAdapter3;
    private String Board3;
    private ArrayList<BoardListItem> items = new ArrayList<>();
    private View view;
    private String date;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_list_fragment, container, false);
        setHasOptionsMenu(true);

        ArrayList<BoardListItem> boardlist = new ArrayList();
        Board3="자유시장";

        final BoardListItem item = new BoardListItem("제목","내용",0);
        db.collection("Community").document("게시판").collection(Board3)
                .orderBy("num", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("test003", document.getId() + " => " + document.getData());
                                //Map<String,Object> map = document.getData();


                                titles[i] = document.get("title").toString();
                                contents[i] = document.get("content").toString();


                                //map. ()
                                //item.setContent(document.getData().toString());
                                i++;
                            }
                        } else {
                            Log.w("test003", "Error getting documents.", task.getException());
                        }
                        setRecyclerView();
                    }
                });
        boardlist.add(item);



        return view;
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
                fragment = new Textboard3();
                loadFragment(fragment);


                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.community_listView);
        //각 Item들이 RecyclerView 의 전체 크기를 변경하지 않는다면
        //setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        //변경될 가능성이 있다면 false로, 없다면 true를 설정해주세요
        recyclerView.setHasFixedSize(true);
        //RecyclerView에 Adapter를 설정해줍니다.
        boardListAdapter3 = new BoardListAdapter3(items, (MainActivity) getActivity());
        recyclerView.setAdapter(boardListAdapter3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d("test001", "여기 실행 되나요?");
        setData();
    }

    private void setData(){
        items.clear();
        Log.d("test001", "dlkandklasndansdlk : " + titles[0]);
        //RecyclerView 에 들어갈 데이터를 추가합니다.
        for(int i=0; i<titles.length; i++)
        {
            if( titles[i] != null ) {
                BoardListItem item = new BoardListItem(titles[i], contents[i], nums[i]);
                items.add(item);
                //데이터 추가가 완료되었으면 notifyDataSetChanged() 메서드를 호출해 데이터 변경 체크를 실시합니다.
                boardListAdapter3.notifyDataSetChanged();
            }
        }
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

