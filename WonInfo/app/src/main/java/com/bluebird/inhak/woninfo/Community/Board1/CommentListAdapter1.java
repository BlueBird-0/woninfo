
package com.bluebird.inhak.woninfo.Community.Board1;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.Community.Textboard.Comment;
import com.bluebird.inhak.woninfo.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class CommentListAdapter1 extends RecyclerView.Adapter<CommentListAdapter1.CommentListViewHolder> {
    private ArrayList<Comment> items;
    private AppCompatActivity activity;

    // 적절한 생성자를 제공합니다(데이터 집합의 종류에 따라 다름
    public CommentListAdapter1(ArrayList<Comment> items, AppCompatActivity activity)
    {
        this.items = items;
        this.activity = activity;
    }

    //새로운 뷰 홀더 생성합니다. (레이아웃 관리자에 의해 호출 됨)
    @Override
    public CommentListAdapter1.CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item_comment, parent, false);
        return new CommentListViewHolder(view);
    }

    //View 의 내용을 해당 포지션의 데이터로 바꿈니다.
    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder holder, int position) {
        holder.item = new Comment();
        holder.item.setWriter_uid(items.get(position).getWriter_uid());
        holder.item.setWriter_id(items.get(position).getWriter_id());
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
        }

        public void DrawItem()
        {
            TextView id = (TextView)itemView.findViewById(R.id.community_comment1_id);
           /* id.setText(item.getWriter_id());*/
            id.setText("익명");
            TextView content= (TextView)itemView.findViewById(R.id.community_comment1_content);
            content.setText(item.getContent());
            TextView date = (TextView)itemView.findViewById(R.id.community_comment1_date);
            date.setText(item.getDate());

            ConstraintLayout boxLayout = (ConstraintLayout)itemView.findViewById(R.id.community_layout_comment);
            boxLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View main_view = (View)v.getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view,"롱클릭 삐융삐융",Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();

                    Log.d("comment",item.getWriter_uid());

                    return true;
                }
            });

            //익명게시판이라 필요없음
            /*ImageView imageView = (ImageView)itemView.findViewById(R.id.community_comment1_profile);
            loadProfile(item.getWriter_uid(), imageView);*/
        }
    }

    public void loadProfile(String uid, final ImageView imageView)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        Task<Uri> riversRef = storageReference.child("profiles/"+uid+"_profile").getDownloadUrl();
        riversRef.addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(imageView);
            }
        });
    }
}

