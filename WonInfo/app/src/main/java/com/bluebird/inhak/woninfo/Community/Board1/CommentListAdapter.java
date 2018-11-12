
package com.bluebird.inhak.woninfo.Community.Board1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.Community.Textboard.Comment;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder> {
    private ArrayList<Comment> items;
    private MainActivity mainActivity;

    // 적절한 생성자를 제공합니다(데이터 집합의 종류에 따라 다름
    public CommentListAdapter(ArrayList<Comment> items, MainActivity mainActivity)
    {
        this.items = items;
        this.mainActivity = mainActivity;
    }

    //새로운 뷰 홀더 생성합니다. (레이아웃 관리자에 의해 호출 됨)
    @Override
    public CommentListAdapter.CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item_comment, parent, false);
        return new CommentListViewHolder(view);
    }

    //View 의 내용을 해당 포지션의 데이터로 바꿈니다.
    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder holder, int position) {
        holder.item = new Comment();
        holder.item.setWriter_photoUri(items.get(position).getWriter_photoUri());
        holder.item.setWriter_uid(items.get(position).getWriter_uid());
        holder.item.setContent(items.get(position).getContent());
        holder.item.setDate(items.get(position).getDate());
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
            /*
            ConstraintLayout button1 = (ConstraintLayout) itemView.findViewById(R.id.community_main);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 게시글에 데이터 넘겨주는 번들 --------------------------------
                    // args 에 넣어서 값 전달.
                    Bundle args = new Bundle();
                    args.putSerializable("Bundle", item);

                    FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    try {
                        Class t = Class.forName("com.bluebird.inhak.woninfo.Community.Board1.BoardViewFragment");
                        Fragment fragment = (Fragment)t.newInstance();
                        fragment.setArguments(args);

                        fragmentTransaction.setCustomAnimations(R.anim.slide_open, 0, 0, R.anim.slide_close);
                        fragmentTransaction.add(R.id.main_fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }catch(Exception e) {}
                }
            });*/
        }

        public void DrawItem()
        {
            TextView id = (TextView)itemView.findViewById(R.id.community_comment1_id);
            id.setText(item.getWriter_uid());
            TextView content= (TextView)itemView.findViewById(R.id.community_comment1_content);
            content.setText(item.getContent());
            TextView date = (TextView)itemView.findViewById(R.id.community_comment1_date);
            date.setText(item.getDate());
        }
    }
}

