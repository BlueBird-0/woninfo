
package com.bluebird.inhak.woninfo.Community.Board3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.Board2.BoardViewActivity2;
import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class BoardListAdapter3 extends RecyclerView.Adapter<BoardListAdapter3.BoardListViewHolder> {
    private ArrayList<BoardListItem> items;
    private MainActivity mainActivity;

    // 적절한 생성자를 제공합니다(데이터 집합의 종류에 따라 다름
    public BoardListAdapter3(ArrayList<BoardListItem> items, MainActivity mainActivity)
    {
        this.items = items;
        this.mainActivity = mainActivity;
    }

    //새로운 뷰 홀더 생성합니다. (레이아웃 관리자에 의해 호출 됨)
    @Override
    public BoardListAdapter3.BoardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_market_list_item, parent, false);
        //ConstraintLayout linearLayout = (ConstraintLayout) view.findViewById(R.id.community_main);
        //linearLayout.addView(new Button(parent.getContext()));
        return new BoardListViewHolder(view);
    }

    //View 의 내용을 해당 포지션의 데이터로 바꿈니다.
    @Override
    public void onBindViewHolder(@NonNull BoardListViewHolder holder, int position) {
        holder.item = new BoardListItem();
        holder.item.setDocumentId(items.get(position).getDocumentId());
        holder.item.setNum(items.get(position).getNum());
        holder.item.setId(items.get(position).getId());
        holder.item.setUid(items.get(position).getUid());

        holder.item.setKinds(items.get(position).getKinds());
        holder.item.setPrice(items.get(position).getPrice());


        holder.item.setTitle(items.get(position).getTitle());
        holder.item.setContent(items.get(position).getContent());
        holder.item.setDate(items.get(position).getDate());

        holder.item.setLikeCount(items.get(position).getLikeCount());
        holder.item.setCommentCount(items.get(position).getCommentCount());
        holder.item.setImageCount(items.get(position).getImageCount());

        holder.DrawItem();  //리스트 안에 아이템 그림그리기 함수
    }

    //데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount() {
        return items.size();
    }



    //커스텀 뷰 홀더
    //item layout에 존재하는 위젯들을 바인딩합니다.
    class BoardListViewHolder extends RecyclerView.ViewHolder{
        private BoardListItem item;

        public BoardListViewHolder(final View itemView){
            super(itemView);
            ConstraintLayout button1 = (ConstraintLayout) itemView.findViewById(R.id.community_market_box);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mainActivity, BoardViewActivity3.class);
                    //args 에 값 넣어서 전달
                    Bundle args = new Bundle();
                    args.putSerializable("Bundle", item);
                    intent.putExtras(args);
                    mainActivity.startActivity(intent);
                }
            });
        }

        public void DrawItem()
        {
            TextView kinds = (TextView) itemView.findViewById(R.id.community_market_kinds);
            kinds.setText(item.getKinds());
            TextView price = (TextView)itemView.findViewById(R.id.community_market_book_price);
            price.setText(item.getPrice() +"￦");

            SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)itemView.findViewById(R.id.community_market_item_refrash);
            refreshLayout.setRefreshing(true);
            ImageView hasImage = (ImageView)itemView.findViewById(R.id.community_market_book_images);
            if(item.getImageCount() == 0)
            {
                hasImage.setVisibility(ImageView.INVISIBLE);
            }else
            {
                loadStoreImages(item.getImageCount(), item.getDocumentId(), hasImage);
            }
        }


        public void loadStoreImages(double imageCount, String documentId, final ImageView imageView)
        {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();

            if(imageCount > 0)
            {
                Task<Uri> storageRef= storageReference.child("board3/" + documentId+"_image_"+0).getDownloadUrl();
                storageRef.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(((Activity)mainContext).getWindow().getDecorView().getRootView())
                                .load(uri)
                                .apply(new RequestOptions().centerCrop())
                                .into(imageView);

                        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)itemView.findViewById(R.id.community_market_item_refrash);
                        refreshLayout.setRefreshing(false);
                        refreshLayout.setEnabled(false);
                        refreshLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("test050", "050505");
                            }
                        });
                    }
                });
            }
        }
    }
}

