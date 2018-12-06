package com.bluebird.inhak.woninfo.Community.Board1;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.Community.Textboard.Comment;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class BoardViewActivity1 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private AppCompatActivity activity;
    private BoardListItem args; //bundle

    private ArrayList<Comment> commentItems = new ArrayList<>();

    private SwipeRefreshLayout swipeRefresh;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_item_document);
        this.activity = this;

        swipeRefresh = findViewById(R.id.community_board1_layout);
        swipeRefresh.setOnRefreshListener(this);
        //뒤로가기 버튼 추가
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        //BoardListItem args = (BoardListItem) savedInstanceState.getSerializable();
        args = (BoardListItem) bundle.getSerializable("Bundle");

        /* args 데이터 입력 */
        TextView id = (TextView) findViewById(R.id.community_board1_id);
        //id.setText(args.getId());
        id.setText("익명");
        TextView title = (TextView) findViewById(R.id.community_board1_title);
        title.setText(args.getTitle());
        TextView content = (TextView) findViewById(R.id.community_board1_content);
        content.setText(args.getContent());
        final TextView date = (TextView) findViewById(R.id.community_board1_date);
        date.setText(args.getDate());
        TextView likeCount = (TextView) findViewById(R.id.community_board1_likecount);
        likeCount.setText(String.valueOf((int) args.getLikeCount()));
        TextView commentCount = (TextView) findViewById(R.id.community_board1_commentcount);
        commentCount.setText(String.valueOf((int) args.getCommentCount()));


        db.collection("Community").document("게시판").collection("대나무숲")
                .document(args.getDocumentId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            //사진 가져오기
                            DocumentSnapshot document = task.getResult();
                            double imageCount = document.getDouble("image_count");
                            loadStoreImages(imageCount, document.getId());
                        }
                    }
                });
        this.onRefresh();
        //댓글 기능 추가
        this.setCommentFunction();
    }

    public void setCommentFunction() {
        final Button commentBtn = (Button)findViewById(R.id.board1_btn_commentwrite);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText commentEdit = (EditText) findViewById(R.id.board1_edit_commentwrite);
                if (commentEdit.getText().toString().equals("")) {
                    Log.d("test040", "값이 없습니다.");
                    View main_view = (View) findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "댓글을 입력해주세요", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext, R.color.Theme_Blue));
                    snackbar.show();
                    return;
                }
                final String content = commentEdit.getText().toString();


                db.collection("Community").document("게시판").collection("대나무숲")
                        .document(args.getDocumentId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                double comment_count = documentSnapshot.getDouble("comment_count");

                                Comment comment = new Comment();
                                comment.setContent(content);
                                comment.setWriter_uid(UserManager.firebaseUser.getUid());
                                comment.setWriter_id(UserManager.firebaseUser.getDisplayName());

                                GregorianCalendar calendar = new GregorianCalendar();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nhh:mm");
                                String now = dateFormat.format(calendar.getTime());
                                comment.setDate(now);


                                db.collection("Community").document("게시판").collection("대나무숲")
                                        .document(args.getDocumentId())
                                        .update("comment" + (int) comment_count, comment.getHashMap(), "comment_count", comment_count + 1);
                                //새로고침 실행
                                onRefresh();
                            }
                        });

                // EditText 내리고, 키보드 닫기
                commentEdit.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);

                View main_view = (View)findViewById(R.id.snackbar_view);
                Snackbar snackbar = Snackbar.make(main_view, "댓글을 작성했습니다.", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext, R.color.Theme_Blue));
                snackbar.show();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                db.collection("Community").document("게시판").collection("대나무숲")
                        .document(args.getDocumentId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();

                                    TextView likeCountText = (TextView) findViewById(R.id.community_board1_likecount);
                                    likeCountText.setText(document.get("like_count").toString());
                                    TextView commentCountText = (TextView) findViewById(R.id.community_board1_commentcount);
                                    commentCountText.setText(String.valueOf((int) (double) document.getDouble("comment_count")));
                                    //익명 프로필 사진이라 필요없음
                                    // if(document.get("uid")!=null) {
                                    //     ImageView imageView = (ImageView)view.findViewById(R.id.community_board1_profile);
                                    //     loadProfile(document.getString("uid"), imageView);
                                    // }

                                    //댓글 가져오기
                                    double commentCount = document.getDouble("comment_count");
                                    commentItems.clear();
                                    for (int i = 0; i < (int) commentCount; i++) {
                                        Map<String, Object> commentMap = (Map<String, Object>) document.get("comment" + i);

                                        Comment comment = new Comment();
                                        comment.setWriter_uid(commentMap.get("writer_uid").toString());
                                        comment.setWriter_id(commentMap.get("writer_id").toString());
                                        comment.setDate(commentMap.get("date").toString());
                                        comment.setContent(commentMap.get("content").toString());
                                        commentItems.add(comment);
                                    }
                                    /* 댓글 recyclerView */
                                    CommentListAdapter1 commentListAdapter;
                                    RecyclerView commentRecycler;
                                    commentRecycler =findViewById(R.id.community_recycler_list);
                                    commentRecycler.setHasFixedSize(true);
                                    //RecyclerView에 Adapter를 설정해줍니다.

                                    commentRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    commentListAdapter = new CommentListAdapter1(commentItems, activity);
                                    commentRecycler.setAdapter(commentListAdapter);
                                    commentRecycler.setNestedScrollingEnabled(false);

                                    swipeRefresh.setRefreshing(false);
                                }
                            }
                        });
            }
        });
    }

    public void loadStoreImages(double imageCount, String documentId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        final LinearLayout imageLinearLayout = findViewById(R.id.community_layout_image);
        imageLinearLayout.removeAllViews();
        for (int i = 0; i < imageCount; i++) {
            Task<Uri> storageRef = storageReference.child("board1/" + documentId + "_image_" + i).getDownloadUrl();
            storageRef.addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d("test098", "success");
                    Log.d("test098", uri.toString());
                    //RecyclerView에 Adapter를 설정해줍니다.

                    ImageView imageView1 = new ImageView(activity);
                    Glide.with(((Activity) mainContext).getWindow().getDecorView().getRootView()).load(uri).into(imageView1);
                    imageLinearLayout.addView(imageView1);

                    LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams) imageView1.getLayoutParams();

                    loparams.leftMargin = 30;
                    loparams.rightMargin = 30;
                    loparams.bottomMargin = 30;
                    imageView1.setLayoutParams(loparams);
                    //사진 등록
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
