package com.bluebird.inhak.woninfo.Home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;

import android.support.v4.app.FragmentManager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bluebird.inhak.woninfo.Community.Board1.BoardListFragment;
import com.bluebird.inhak.woninfo.Community.Board2.BoardListFragment2;
import com.bluebird.inhak.woninfo.Community.Board3.BoardListFragment3;

import com.bluebird.inhak.woninfo.Dictionary.A08Fragment.A08Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A15Fragment.A15Fragment;


import com.bluebird.inhak.woninfo.Dictionary.A08Fragment.A08Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A15Fragment.A15Fragment;
import com.bluebird.inhak.woninfo.Dictionary.A20Fragment.A20Fragment;
import com.bluebird.inhak.woninfo.Dictionary.DictionaryMainFragment;

import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class HomeMainFragment extends Fragment {
    ConstraintLayout button1;
    ConstraintLayout button2;
    ConstraintLayout button3;
    ConstraintLayout button4;

    AutoScrollViewPager autoViewPager;
    static Fragment fragment = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_main_fragment, container, false);
        button1 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn1);
        button2 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn2);
        button3 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn3);
        button4 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new A15Fragment();
                loadFragment(fragment);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager2 = getFragmentManager();
                Fragment fragment = null;
                fragment = new A08Fragment();
                loadFragment(fragment);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager3 = getFragmentManager();
                Fragment fragment = null;
                fragment = new A08Fragment();
                loadFragment(fragment);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager testlistview = getFragmentManager();
                Fragment fragment = null;
                fragment = new BoardListFragment();
                loadFragment(fragment);

            }
        });

        //  WebView webView = (WebView)v.findViewById(R.id.youtube_player_view);
        // webView.getSettings().setJavaScriptEnabled(true);
        // webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        // webView.setWebViewClient(new WebViewClient());
        // webView.loadUrl("https://www.youtube.com/watch?v=tcuLThIyA70");


        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        data.add("https://upload.wikimedia.org/wikipedia/en/thumb/2/24/SpongeBob_SquarePants_logo.svg/1200px-SpongeBob_SquarePants_logo.svg.png");
        data.add("http://nick.mtvnimages.com/nick/promos-thumbs/videos/spongebob-squarepants/rainbow-meme-video/spongebob-rainbow-meme-video-16x9.jpg?quality=0.60");
        data.add("http://nick.mtvnimages.com/nick/video/images/nick/sb-053-16x9.jpg?maxdimension=&quality=0.60");
        data.add("https://www.gannett-cdn.com/-mm-/60f7e37cc9fdd931c890c156949aafce3b65fd8c/c=243-0-1437-898&r=x408&c=540x405/local/-/media/2017/03/14/USATODAY/USATODAY/636250854246773757-XXX-IMG-WTW-SPONGEBOB01-0105-1-1-NC9J38E8.JPG");

        autoViewPager = (AutoScrollViewPager) v.findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(getContext(), data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(5000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작


        ConstraintLayout topBotton1 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn1);
        topBotton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PackageManager pm = getActivity().getPackageManager();
                    pm.getApplicationInfo("kr.co.libtech.sponge", PackageManager.GET_META_DATA);
                    Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("kr.co.libtech.sponge");
                    startActivity(launchIntent);
                } catch (PackageManager.NameNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=kr.co.libtech.sponge"));
                    startActivity(intent);
                }
            }
        });

        ConstraintLayout topBotton2 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn2);
        topBotton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://elibrary.wku.ac.kr/lib/SlimaPlus.csp"));
                startActivity(intent);
            }
        });

        ConstraintLayout topBotton3 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn3);
        topBotton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wvu.wku.ac.kr"));
                startActivity(intent);
            }
        });

        ConstraintLayout mainBotton1 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn1);
        ConstraintLayout mainBotton2 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn2);
        ConstraintLayout mainBotton3 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn3);
        ConstraintLayout mainBotton4 = (ConstraintLayout) v.findViewById(R.id.home_layout_smallbtn4);


        mainBotton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new A15Fragment();
                loadFragment(fragment);

            }
        });

        mainBotton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new A08Fragment();
                loadFragment(fragment);

            }
        });


        return v;
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
