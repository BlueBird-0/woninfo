package com.bluebird.inhak.woninfo.Dictionary.A22Fragment;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bluebird.inhak.woninfo.R;
//TODO 22Fragment 사용 안함
/**
 * Created by InHak on 2017-12-31.
 */

public class A22Fragment extends Fragment {
    static private boolean WEBVIEW_STATE_OPENED = false;
    static View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a_22_fragment, container, false);

        WebView webView = (WebView)view.findViewById(R.id.webview2);
        webView.setWebViewClient(new WebViewClient());

        Log.d("test1", "start");


        new Thread() {
            public void run()
            {
                //WebParser webparser = new WebParser();
                //webparser.Login("ije5581", "Jeon3512!");
                //Document document = webparser.getDomitoryPage();
                //Log.d("blog", document.getAllElements().toString());
            }
        }.start();





        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.loadUrl("http://cyber.wku.ac.kr/WEB/Cyber/CoBoard_V005/b.html");
        return view;
    }
}

