package com.bluebird.inhak.woninfo.Community.Board3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.Textboard.Comment;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

public class CommentListAdapter3 extends RecyclerView.Adapter<CommentListAdapter3.CommentListViewHolder> {
    private ArrayList<Comment> items;
    private AppCompatActivity activity;

    // 적절한 생성자를 제공합니다(데이터 집합의 종류에 따라 다름
    public CommentListAdapter3(ArrayList<Comment> items, AppCompatActivity activity)
    {
        this.items = items;
        this.activity = activity;
    }

    //새로운 뷰 홀더 생성합니다. (레이아웃 관리자에 의해 호출 됨)
    @Override
    public CommentListAdapter3.CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item_comment, parent, false);
        return new CommentListViewHolder(view);
    }

    //View 의 내용을 해당 포지션의 데이터로 바꿈니다.
    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder holder, int position) {
        Log.d("test040", "여기도 실행1.0");
        holder.item = new Comment();
        holder.item.setWriter_uid(items.get(position).getWriter_uid());
        holder.item.setWriter_id(items.get(position).getWriter_id());
        holder.item.setContent(items.get(position).getContent());
        holder.item.setDate(items.get(position).getDate());
        Log.d("test040", "여기도 실행1");
        holder.DrawItem();  //리스트 안에 아이템 그림그리기 함수
    }

    //데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return items.size();
    }



    //커스텀 뷰 홀더
    //item layout에 존재하는 위젯들을 바인딩합니다.
    class CommentListViewHolder extends RecyclerView.ViewHolder{
        private Comment item;

        public CommentListViewHolder(final View itemView){
            super(itemView);
        }

        public void DrawItem()
        {
            Log.d("test040", "여기도 실행2");
            TextView id = (TextView)itemView.findViewById(R.id.community_comment1_id);
            id.setText(item.getWriter_id());
            TextView content= (TextView)itemView.findViewById(R.id.community_comment1_content);
            content.setText(item.getContent());
            TextView date = (TextView)itemView.findViewById(R.id.community_comment1_date);
            date.setText(item.getDate());
        }
    }
}

