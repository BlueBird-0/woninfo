package com.bluebird.inhak.woninfo.Community;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

public class BoardListFragment extends Fragment{
    private String[] titles = {"title1", "이게 제목", "이것도 제목", "이것도 제목", "이것도 제목", "이것도 제목", "이것도 제목"};
    private BoardListAdapter boardListAdapter;

    private ArrayList<BoardListItem> items = new ArrayList<>();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_list_fragment, container, false);

        setRecyclerView();

        return view;
    }

    private void setRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.community_listView);
        //각 Item들이 RecyclerView 의 전체 크기를 변경하지 않는다면
        //setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        //변경될 가능성이 있다면 false로, 없다면 true를 설정해주세요
        recyclerView.setHasFixedSize(true);

        //RecyclerView에 Adapter를 설정해줍니다.
        boardListAdapter = new BoardListAdapter(items);
        recyclerView.setAdapter(boardListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setData();
    }

    private void setData(){
        items.clear();
        //RecyclerView 에 들어갈 데이터를 추가합니다.
        for (String title: titles)
        {
            items.add(new BoardListItem(title));
        }
        //데이터 추가가 완료되었으면 notifyDataSetChanged() 메서드를 호출해 데이터 변경 체크를 실시합니다.
        boardListAdapter.notifyDataSetChanged();
    }
}
