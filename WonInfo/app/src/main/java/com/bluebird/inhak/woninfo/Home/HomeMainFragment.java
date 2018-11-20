package com.bluebird.inhak.woninfo.Home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;

import android.support.v4.app.FragmentManager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.bluebird.inhak.woninfo.Community.Board1.BoardListAdapter;
import com.bluebird.inhak.woninfo.Community.Board1.BoardListFragment;
import com.bluebird.inhak.woninfo.Community.Board2.BoardListFragment2;
import com.bluebird.inhak.woninfo.Community.Board3.BoardListFragment3;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.Dictionary.A08Fragment.A08Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A15Fragment.A15Fragment;


import com.bluebird.inhak.woninfo.Dictionary.A08Fragment.A08Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A15Fragment.A15Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A20Fragment.A20Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A24Fragment.A24Fragment;
import com.bluebird.inhak.woninfo.Dictionary.DictionaryMainFragment;

import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class HomeMainFragment extends Fragment {

    private NewsAdapter newsAdapter;
    private ArrayList<BoardListItem> newsItems = new ArrayList<>();
    AutoScrollViewPager autoViewPager;
    private SwipeRefreshLayout swipeRefresh;
    static Fragment fragment = null;
    private View view;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_main_fragment, container, false);
        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        data.add("https://upload.wikimedia.org/wikipedia/en/thumb/2/24/SpongeBob_SquarePants_logo.svg/1200px-SpongeBob_SquarePants_logo.svg.png");
        data.add("http://nick.mtvnimages.com/nick/promos-thumbs/videos/spongebob-squarepants/rainbow-meme-video/spongebob-rainbow-meme-video-16x9.jpg?quality=0.60");
        data.add("http://nick.mtvnimages.com/nick/video/images/nick/sb-053-16x9.jpg?maxdimension=&quality=0.60");
        data.add("https://www.gannett-cdn.com/-mm-/60f7e37cc9fdd931c890c156949aafce3b65fd8c/c=243-0-1437-898&r=x408&c=540x405/local/-/media/2017/03/14/USATODAY/USATODAY/636250854246773757-XXX-IMG-WTW-SPONGEBOB01-0105-1-1-NC9J38E8.JPG");

        autoViewPager = (AutoScrollViewPager) view.findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(getContext(), data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작


        ConstraintLayout topButton1 = (ConstraintLayout) view.findViewById(R.id.home_layout_smallbtn1);
        topButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    PackageManager pm = getActivity().getPackageManager();
                    pm.getApplicationInfo("kr.co.libtech.sponge", PackageManager.GET_META_DATA);
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("kr.co.libtech.sponge");
                    startActivity(launchIntent);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=kr.co.libtech.sponge"));
                    startActivity(intent);
                }
            }
        });

        ConstraintLayout topButton2 = (ConstraintLayout) view.findViewById(R.id.home_layout_smallbtn2);
        topButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://elibrary.wku.ac.kr/lib/SlimaPlus.csp"));
                startActivity(intent);
            }
        });

        ConstraintLayout topButton3 = (ConstraintLayout) view.findViewById(R.id.home_layout_smallbtn3);
        topButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wvu.wku.ac.kr"));
                startActivity(intent);
            }
        });

        ConstraintLayout topButton4 = (ConstraintLayout)view.findViewById(R.id.home_layout_smallbtn4);
        topButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment bus = new A24Fragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container,bus)
                        .commit();

            }
        });



        setRecyclerView();
        onRefresh();
        return view;
    }

    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                newsAdapter = new NewsAdapter(newsItems,(MainActivity) getActivity());
                final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.Listview);

                db.collection("Community").document("게시판").collection("대나무숲")

                        .orderBy("num", Query.Direction.DESCENDING)
                        .limit(2)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task){
                                if(task.isSuccessful()){

                                    for(DocumentSnapshot document:task.getResult()){
                                        BoardListItem item = new BoardListItem();
                                        item.setDocumentId(document.getId());

                                        item.setBoard(String.valueOf(("대나무숲")));
                                        item.setTitle(document.get("title").toString());

                                        item.setId(document.get("id").toString());
                                        item.setContent(document.get("content").toString());
                                        item.setDate(document.get("date").toString());
                                        item.setLikeCount(document.getDouble("like_count"));
                                        item.setCommentCount(document.getDouble("comment_count"));
                                        newsItems.add(item);

                                        recyclerView.setAdapter(newsAdapter);
                                    }
                                }
                            }
                        });
                db.collection("Community").document("게시판").collection("자유게시판")
                        .orderBy("num", Query.Direction.DESCENDING)
                        .limit(2)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task){
                                if(task.isSuccessful()){

                                    for(DocumentSnapshot document:task.getResult()){
                                        BoardListItem item = new BoardListItem();
                                        item.setDocumentId(document.getId());

                                        item.setBoard(String.valueOf(("자유게시판")));
                                        item.setTitle(document.get("title").toString());

                                        item.setId(document.get("id").toString());
                                        item.setContent(document.get("content").toString());
                                        item.setDate(document.get("date").toString());
                                        item.setLikeCount(document.getDouble("like_count"));
                                        item.setCommentCount(document.getDouble("comment_count"));


                                        newsItems.add(item);

                                        recyclerView.setAdapter(newsAdapter);
                                    }
                                }
                            }
                        }); db.collection("Community").document("게시판").collection("자유시장")
                        .orderBy("num", Query.Direction.DESCENDING)
                        .limit(2)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task){
                                if(task.isSuccessful()){

                                    for(DocumentSnapshot document:task.getResult()){
                                        BoardListItem item = new BoardListItem();
                                        item.setDocumentId(document.getId());

                                        item.setBoard(String.valueOf(("자유시장")));
                                        item.setTitle(document.get("title").toString());

                                        item.setId(document.get("id").toString());
                                        item.setContent(document.get("content").toString());
                                        item.setDate(document.get("date").toString());
                                        item.setCommentCount(document.getDouble("comment_count"));


                                        newsItems.add(item);

                                        recyclerView.setAdapter(newsAdapter);
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void setRecyclerView(){
        ArrayList items = new ArrayList<>();








        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.Listview);
        //각 Item들이 RecyclerView 의 전체 크기를 변경하지 않는다면
        //setHasFixedSize() 함수를 사용해서 성능을 개선할 수 있습니다.
        //변경될 가능성이 있다면 false로, 없다면 true를 설정해주세요
        recyclerView.setHasFixedSize(false);
        //RecyclerView에 Adapter를 설정해줍니다.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //newsAdapter = new NewsAdapter(newsItems,(MainActivity) getActivity());
        newsAdapter = new NewsAdapter(items,(MainActivity) getActivity());

        recyclerView.setAdapter(newsAdapter);
        //recyclerView.setAdapter(newsAdapter2);

        recyclerView.setNestedScrollingEnabled(false);
        //setData();
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getFragmentManager().beginTransaction().addToBackStack(null);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}


    /*
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View fragmentYoutubeView = inflater.inflate(R.layout.home_main_fragment, container, false);
        mYoutubePlayerFragment = new YouTubePlayerSupportFragment();
        mYoutubePlayerFragment.initialize(youtubeKey, this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtube_player_view, mYoutubePlayerFragment);
        fragmentTransaction.commit();

        return fragmentYoutubeView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            if(!b){
                youTubePlayer.cueVideo("tcuLThIyA70");
            }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
            if (result.isUserRecoverableError()) {
                result.getErrorDialog(this.getActivity(),1).show();
            } else {
                Toast.makeText(this.getActivity(),
                        "YouTubePlayer.onInitializationFailure(): " + result.toString(),
                        Toast.LENGTH_LONG).show();
            }
    }
*/

