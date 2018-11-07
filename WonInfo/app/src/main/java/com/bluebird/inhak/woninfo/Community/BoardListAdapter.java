/*
package com.bluebird.inhak.woninfo.Community;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.BoardListViewHolder> {
    private ArrayList<BoardListItem> items;


    // 적절한 생성자를 제공합니다(데이터 집합의 종류에 따라 다름
    public BoardListAdapter(ArrayList<BoardListItem> items)
    {
        this.items = items;
    }

    //새로운 뷰 홀더 생성합니다. (레이아웃 관리자에 의해 호출 됨)
    @Override
    public BoardListAdapter.BoardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_list_item, parent, false);
        return new BoardListViewHolder(view);
    }

    //View 의 내용을 해당 포지션의 데이터로 바꿈니다.
    @Override
    public void onBindViewHolder(@NonNull BoardListViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.content.setText(items.get(position).getContent());

    }

    //데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return items.size();
    }



    //커스텀 뷰 홀더
    //item layout에 존재하는 위젯들을 바인딩합니다.
    class BoardListViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView content;

        public BoardListViewHolder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.community_list_item_title);
            content = (TextView)itemView.findViewById(R.id.community_list_item_content);
        }
    }

    public interface OnLoadMoreItems {
        void onLoadMore();
    }

    public void setRecyclerView(RecyclerView recyclerView){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test031","scrolled");
            }
        });
    }
}
*/
