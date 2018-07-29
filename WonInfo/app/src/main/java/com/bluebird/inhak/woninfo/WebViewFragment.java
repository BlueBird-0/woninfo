package com.bluebird.inhak.woninfo;

import android.app.Fragment;
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
        FloatingActionButton floatingActionButton = view.getRootView().findViewById(R.id.fab);
        Animation fab_open = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
        floatingActionButton.startAnimation(fab_open);
        if(WEBVIEW_STATE_OPENED == true)
        {
            webView.setVisibility(View.GONE);
            floatingActionButton.setImageResource(R.drawable.ic_webview_o);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.colorAccent)));
        }else if(WEBVIEW_STATE_OPENED == false)
        {
            webView.setVisibility(View.VISIBLE);
            floatingActionButton.setImageResource(R.drawable.ic_webview_c);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(view.getResources().getColor(R.color.colorPrimary)));
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

        //
        //new Thread(new Runnable() {
         //   @Override
         //   public void run() {
                /*
                WebParser webparser = new WebParser();
                webparser.Login("ije5581", "Jeon3512!");
                Document document = webparser.getDomitoryPage();
                Log.d("test33", document.getAllElements().toString());
                Log.d("test33", webparser.loginCookie.toString());

                CookieManager cookieManager = null;
                CookieSyncManager cookieSyncManager = null;
                cookieSyncManager = CookieSyncManager.createInstance(view.getContext());
                cookieSyncManager.sync();
                cookieManager = CookieManager.getInstance();
                String strCookies = webparser.loginCookie.toString();
                strCookies = strCookies.substring(1, strCookies.length()-1);
                Log.d("test33", "test: "+strCookies);

                String[] strCookiess = strCookies.split(", ");
                for(int i=0; i<strCookiess.length; i++)
                {
                    cookieManager.setCookie("http://intra.wku.ac.kr", strCookiess[i]);
                    Log.d("test33","Cookies : "+strCookiess[i]);
                }
                CookieSyncManager.getInstance().sync();
                Log.d("test33","CCCCCCCCCC : "+CookieManager.getInstance().getCookie("http://intra.wku.ac.kr/SWupis/V005/main.jsp?tmep="));

*/
           //     final Map<String, String> extraHeaders = new HashMap<String, String>();
           //     extraHeaders.put("Origin", "http://intra.wku.ac.kr");
           //     extraHeaders.put("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp");
           //     extraHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
          //      extraHeaders.put("Content-Type", "application/x-www-form-urlencoded");
           //     extraHeaders.put("Accept-Encoding", "gzip, deflate, br");
           //     extraHeaders.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
          //      extraHeaders.put("Cache-Control", "max-age=0");
          //      extraHeaders.put("Connection", "keep-alive");
           //     extraHeaders.put("Content-Length", "104");

           //   getActivity().runOnUiThread(new Runnable(){
               //     public void run(){
              //          WebView webView = (WebView)view.findViewById(R.id.webview);
                        //webView.reload();
              //          webView.loadUrl("http://intra.wku.ac.kr",extraHeaders);
           //         }
        //        });
       //     }
     //   }).start();

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
                    //"document.getElementById(\"btn\").click();" +
                    //"var button = document.getElementsByClassName(\"btn\");" +
                    //"for (var i=0;i<button.length; i++) {\n" +
                    //"    button[i].click();\n" +
                    //"};";
            view.evaluateJavascript(Script, null);
            Log.d("test33","d");
        }
    }
}
