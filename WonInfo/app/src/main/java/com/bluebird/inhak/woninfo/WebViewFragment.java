package com.bluebird.inhak.woninfo;

import android.support.v4.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by InHak on 2017-12-31.
 */

public class WebViewFragment extends Fragment {
    static private boolean WEBVIEW_STATE_OPENED = false;
    static View view;
    static public void changeState()
    {
        WebView webView = (WebView)view.findViewById(R.id.webview);
        if(WEBVIEW_STATE_OPENED == true)
        {
            webView.setVisibility(View.GONE);
        }else if(WEBVIEW_STATE_OPENED == false)
        {
            webView.setVisibility(View.VISIBLE);
        }
        WEBVIEW_STATE_OPENED = !WEBVIEW_STATE_OPENED;
        view.bringToFront();
    }
    static public boolean webViewIsOpened()
    {
        return WEBVIEW_STATE_OPENED;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.webview_fragment, container, false);

        WebView webView = (WebView)view.findViewById(R.id.webview);

        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
        cookieSyncManager.sync();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setSaveFormData(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);


        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.setInitialScale(300);
        webView.setX((float)0.5);
        webView.loadUrl("http://intra.wku.ac.kr");

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }

    @Override
    public void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
    }



    protected class ViewClient extends WebViewClient
    {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            String Script = "var id = document.getElementById(\"userid\");id.value = \"" + "ije5581" + "\";" +
                    "var pw = document.getElementById(\"passwd\");pw.value = \"" + "Jeon3512!" + "\";" +
                    "};";
            view.evaluateJavascript(Script, null);
            Log.d("test33","d");
        }
    }
}
