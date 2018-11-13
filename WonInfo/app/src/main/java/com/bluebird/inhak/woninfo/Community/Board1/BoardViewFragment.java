
package com.bluebird.inhak.woninfo.Community.Board1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.Community.Textboard.Comment;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class BoardViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    static private FirebaseAuth auth;

    private BoardListItem args; //bundle

    private ArrayList<Comment> commentItems = new ArrayList<>();

    private View view;
    private SwipeRefreshLayout swipeRefresh;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_item_document, container, false);
        setHasOptionsMenu(true);
        args = (BoardListItem)getArguments().getSerializable("Bundle");

        swipeRefresh = view.findViewById(R.id.community_board1_layout);
        swipeRefresh.setOnRefreshListener(this);

        /* args 데이터 입력 */
        TextView id= (TextView) view.findViewById(R.id.community_board1_id);
        id.setText(args.getId());
        TextView title= (TextView) view.findViewById(R.id.community_board1_title);
        title.setText(args.getTitle());
        TextView content= (TextView) view.findViewById(R.id.community_board1_content);
        content.setText(args.getContent());
        final TextView date= (TextView) view.findViewById(R.id.community_board1_date);
        date.setText(args.getDate());
        TextView likeCount= (TextView) view.findViewById(R.id.community_board1_likecount);
        likeCount.setText(String.valueOf((int)args.getLikeCount()));
        TextView commentCount= (TextView) view.findViewById(R.id.community_board1_commentcount);
        commentCount.setText(String.valueOf((int)args.getCommentCount()));


        double imageCount = args.getImageCount();

        this.onRefresh();
        //댓글 기능 추가
        this.setCommentFunction();
        return view;
    }

    public void setCommentFunction()
    {
        final Button commentBtn = (Button)view.findViewById(R.id.board1_btn_commentwrite);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText commentEdit = (EditText)view.findViewById(R.id.board1_edit_commentwrite);
                if(commentEdit.getText().toString().equals(null))
                {
                    Log.d("test040","값이 없습니다.");
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
                                comment.setWriter_photoUri(UserManager.firebaseUser.getPhotoUrl().toString());

                                GregorianCalendar calendar = new GregorianCalendar();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM\nhh:mm");
                                String now = dateFormat.format(calendar.getTime());
                                comment.setDate(now);


                                db.collection("Community").document("게시판").collection("대나무숲")
                                        .document(args.getDocumentId())
                                        .update("comment"+(int)comment_count, comment.getHashMap(), "comment_count",comment_count+1);
                            }
                        });

                // EditText 내리고, 키보드 닫기
                commentEdit.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);

                View main_view = (View)getView().getRootView().findViewById(R.id.snackbar_view);
                Snackbar snackbar = Snackbar.make(main_view, "댓글을 작성했습니다.", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
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
                                if(task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    Log.d("test040", args.getId());

                                    TextView likeCountText= (TextView) view.findViewById(R.id.community_board1_likecount);
                                    likeCountText.setText(document.get("like_count").toString());
                                    TextView commentCountText= (TextView) view.findViewById(R.id.community_board1_commentcount);
                                    commentCountText.setText(String.valueOf((int)(double)document.getDouble("comment_count")));
                                    ImageView imageView = (ImageView)view.findViewById(R.id.community_board1_profile);
                                    if(document.get("profile")!=null) {
                                        Glide.with(getActivity()).load(document.get("profile").toString()).into(imageView);
                                    }



                                    //사진 가져오기
                                    double imageCount = document.getDouble("image_count");
                                    //for(int i=0; i< (int)imageCount; i++)
                                    for(int i=0; i< 4; i++)
                                    {
                                        LinearLayout imageLinearLayout;
                                        imageLinearLayout = view.findViewById(R.id.community_layout_image);
                                        //RecyclerView에 Adapter를 설정해줍니다.

                                        ImageView imageView1= new ImageView(getContext());
                                        Glide.with(((Activity)mainContext).getWindow().getDecorView().getRootView()).load(UserManager.firebaseUser.getPhotoUrl()).into(imageView1);
                                        imageLinearLayout.addView(imageView1);
//                                        imageLinearLayout.addView(button);
  //                                      imageLinearLayout.addView(button);
                                    }


                                    //댓글 가져오기
                                    double commentCount = document.getDouble("comment_count");
                                    commentItems.clear();
                                    for(int i=0; i< (int)commentCount; i++)
                                    {
                                        Map<String, Object> commentMap = (Map<String, Object>) document.get("comment"+i);
                                        Log.d("test040", "1:"+commentMap.get("writer_uid").toString());
                                        Log.d("test040", "1:"+commentMap.get("writer_id").toString());
                                        Log.d("test040", "1:"+commentMap.get("writer_photoUri").toString());
                                        Log.d("test040", "1:"+commentMap.get("date").toString());
                                        Log.d("test040", "1:"+commentMap.get("content").toString());


                                        Comment comment = new Comment();
                                        comment.setWriter_uid( commentMap.get("writer_uid").toString());
                                        comment.setWriter_id( commentMap.get("writer_id").toString());
                                        comment.setWriter_photoUri( commentMap.get("writer_photoUri").toString());
                                        comment.setDate( commentMap.get("date").toString());
                                        comment.setContent( commentMap.get("content").toString());
                                        commentItems.add(comment);
                                    }
                                    /* 댓글 recyclerView */
                                    CommentListAdapter commentListAdapter;
                                    RecyclerView commentRecycler;
                                    commentRecycler = view.findViewById(R.id.community_recycler_list);
                                    commentRecycler.setHasFixedSize(true);
                                    //RecyclerView에 Adapter를 설정해줍니다.

                                    commentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    commentListAdapter = new CommentListAdapter(commentItems,(MainActivity)getActivity());
                                    commentRecycler.setAdapter(commentListAdapter);
                                    commentRecycler.setNestedScrollingEnabled(false);

                                    swipeRefresh.setRefreshing(false);
                                }
                            }
                        });
            }
        });
/*
        db.collection("Community").document("게시판").collection("대나무숲")
                .whereEqualTo("num", args.getNum())   //////////////////// 여기 시발놈
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                ImageView imageView = (ImageView)view.findViewById(R.id.community_board1_profile);


                                Log.d("test040", user.getPhotoUrl().toString());


                                if(user.getPhotoUrl() != null)
                                    Glide.with(getActivity()).load(user.getPhotoUrl()).into(imageView);

                                dates = document.get("date").toString();
                                TextView dateText= (TextView) view.findViewById(R.id.community_board1_date);
                                dateText.setText(dates);

*/
                                /* 댓글 쓰기 버튼 */
        /*
                                Button commentBtn = (Button)view.findViewById(R.id.board1_btn_commentwrite);
                                commentBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        EditText commentEdit = (EditText)view.findViewById(R.id.board1_edit_commentwrite);
                                        if(commentEdit.getText().toString().equals(null))
                                        {
                                            Log.d("test040","값이 없습니다.");
                                            return;
                                        }
                                        Map<String, Object> 대나무숲 = new HashMap<>();

                                        Comment comment = new Comment();
                                        comment.setContent("댓글 제목");
                                        comment.setWriter_uid("testUid");
                                        comment.setWriter_photoUri("사진 uri");

                                        db.collection("Community").document("게시판").collection("대나무숲")
                                                .document("0tF1Lbej8V0JLSjVG4bj")
                                                .update("댓글제목",comment.getHashMap());

                                    }
                                });
                            }
                        }
                    }
                });
*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}

