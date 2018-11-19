
package com.bluebird.inhak.woninfo.Home;

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

import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<BoardListItem> items;
    private MainActivity mainActivity;

    // 적절한 생성자를 제공합니다(데이터 집합의 종류에 따라 다름
    public NewsAdapter(ArrayList<BoardListItem> items, MainActivity mainActivity)
    {
        this.items = items;
        this.mainActivity = mainActivity;
    }

    //새로운 뷰 홀더 생성합니다. (레이아웃 관리자에 의해 호출 됨)
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_main_list_item, parent, false);
        //ConstraintLayout linearLayout = (ConstraintLayout) view.findViewById(R.id.community_main);
        //linearLayout.addView(new Button(parent.getContext()));
        return new NewsViewHolder(view);
    }

    //View 의 내용을 해당 포지션의 데이터로 바꿈니다.
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        holder.item = new BoardListItem();
        holder.item.setBoard(items.get(position).getBoard());
        holder.item.setTitle(items.get(position).getTitle());
        holder.item.setDocumentId(items.get(position).getDocumentId());
        holder.item.setNum(items.get(position).getNum());
        holder.item.setId(items.get(position).getId());
        holder.item.setUid(items.get(position).getUid());

        holder.item.setContent(items.get(position).getContent());
        holder.item.setDate(items.get(position).getDate());

        holder.item.setLikeCount(items.get(position).getLikeCount());
        holder.item.setCommentCount(items.get(position).getCommentCount());
        holder.item.setImageCount(items.get(position).getImageCount());/*

*/
        //holder.item.setImageCount(items.get(position).getImageCount());

        holder.DrawItem();  //리스트 안에 아이템 그림그리기 함수
    }

    //데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return items.size();
    }



    //커스텀 뷰 홀더
    //item layout에 존재하는 위젯들을 바인딩합니다.
    class NewsViewHolder extends RecyclerView.ViewHolder{
        private BoardListItem item;


        public NewsViewHolder(final View itemView){
            super(itemView);
            ConstraintLayout button1 = (ConstraintLayout) itemView.findViewById(R.id.home_main_list_item);
            button1.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                    Bundle args = new Bundle();
                    args.putSerializable("Bundle", item);
                    FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Log.d("test050", item.getBoard());
                    Class t = null;
                    try {
                        if (item.getBoard().equals("대나무숲")) {
                            t = Class.forName("com.bluebird.inhak.woninfo.Community.Board1.BoardViewFragment");
                        } else if (item.getBoard().equals("자유게시판")) {
                            t = Class.forName("com.bluebird.inhak.woninfo.Community.Board2.BoardViewFragment2");
                        }else if(item.getBoard().equals("자유시장")) {
                            t = Class.forName("com.bluebird.inhak.woninfo.Community.Board3.BoardViewFragment3");
                        }
                        Fragment fragment = (Fragment) t.newInstance();
                        fragment.setArguments(args);



                        fragmentTransaction.setCustomAnimations(R.anim.slide_open, 0, 0, R.anim.slide_close);
                        fragmentTransaction.add(R.id.main_fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }catch(Exception e) {}
                    }
                    });

        }

        public void DrawItem()
        {
            TextView title = (TextView)itemView.findViewById(R.id.home_main_text_title);
            title.setText(item.getTitle());
            TextView board = (TextView)itemView.findViewById(R.id.home_main_text_broadname1);
            board.setText(item.getBoard());

            /*TextView likeCount = (TextView)itemView.findViewById(R.id.community_list_item_likecount);
            likeCount.setText(String.valueOf((int)item.getLikeCount()));
            TextView commentCount= (TextView)itemView.findViewById(R.id.community_list_item_commentcount);
            commentCount.setText(String.valueOf((int)item.getCommentCount()));
            ImageView hasImage = (ImageView)itemView.findViewById(R.id.community_list_item_image);
            if(item.getImageCount() == 0)
            {
                hasImage.setVisibility(ImageView.INVISIBLE);
            }*/
        }
    }
}

