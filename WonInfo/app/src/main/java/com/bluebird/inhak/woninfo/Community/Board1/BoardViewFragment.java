
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
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.Community.Textboard.Comment;
import com.bluebird.inhak.woninfo.R;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

public class BoardViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO 여기 String 으로 옮겨야함
    //static int PAGE_COUNT = 1;  //한페이지에 보여주는 게시글 수


    static  private String dates;
    static private FirebaseAuth auth;
    static private FirebaseUser firebaseUser;
    static private FirebaseAuth.AuthStateListener mAuthListener;

    private BoardListItem args; //bundle

    private BoardListAdapter boardListAdapter;
    private String Board;
    private ArrayList<BoardListItem> items = new ArrayList<>();
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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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

                Comment comment = new Comment();
                comment.setContent(commentEdit.getText().toString());
                comment.setWriter_uid(firebaseUser.getUid());
                comment.setWriter_photoUri(firebaseUser.getPhotoUrl().toString());

                // EditText 내리고, 키보드 닫기
                commentEdit.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);

                db.collection("Community").document("게시판").collection("대나무숲")
                        .document(args.getDocumentId())
                        .update("댓글제목",comment.getHashMap());

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

                                    TextView likeCount= (TextView) view.findViewById(R.id.community_board1_likecount);
                                    likeCount.setText(document.get("like_count").toString());
                                    TextView commentCount= (TextView) view.findViewById(R.id.community_board1_commentcount);
                                    commentCount.setText(document.get("comment_count").toString());
                                    ImageView imageView = (ImageView)view.findViewById(R.id.community_board1_profile);
                                    if(document.get("profile")!=null) {
                                        Glide.with(getActivity()).load(document.get("profile").toString()).into(imageView);
                                        Log.d("test040", "사진설정(경로 못찾을 때 바꿔야함): "+document.get("profile").toString());
                                    }
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

