
package com.bluebird.inhak.woninfo.Community.Board4;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

public class BoardListAdapter4 extends RecyclerView.Adapter<BoardListAdapter4.BoardListViewHolder> {
    private ArrayList<BoardListItem> items;
    private MainActivity mainActivity;

    // 적절한 생성자를 제공합니다(데이터 집합의 종류에 따라 다름
    public BoardListAdapter4(ArrayList<BoardListItem> items, MainActivity mainActivity)
    {
        this.items = items;
        this.mainActivity = mainActivity;
    }

    //새로운 뷰 홀더 생성합니다. (레이아웃 관리자에 의해 호출 됨)
    @Override
    public BoardListAdapter4.BoardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    try {
                        Class t = Class.forName("com.bluebird.inhak.woninfo.Community.Board4.BoardViewFragment4");
                        //Class t = Class.forName("com.example.inhak.woninfo."+"A09"+"Fragment"+".A09"+"Fragment");
                        Fragment fragment = (Fragment)t.newInstance();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_open, 0, 0, R.anim.slide_close);
                        fragmentTransaction.add(R.id.main_fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }catch(Exception e) {}
                }
            });
            content = (TextView)itemView.findViewById(R.id.community_list_item_content);
        }
    }
}

