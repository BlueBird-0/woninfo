package com.bluebird.inhak.woninfo.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class HomeMainFragment extends Fragment implements  YouTubePlayer.OnInitializedListener{


    YouTubePlayerSupportFragment mYoutubePlayerFragment;

    public static final String ARG_ITEM_ID = "item_id";
    public String youtubeKey = "AIzaSyAU98php0k0e6lWkw_he1RHMgilmvzRc1U";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View fragmentYoutubeView = inflater.inflate(R.layout.home_main_fragment, container, false);
        mYoutubePlayerFragment = new YouTubePlayerSupportFragment();
        mYoutubePlayerFragment.initialize(youtubeKey, this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_youtube_player, mYoutubePlayerFragment);
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



}