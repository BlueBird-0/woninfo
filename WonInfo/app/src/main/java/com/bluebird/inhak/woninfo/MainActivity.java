package com.bluebird.inhak.woninfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.Community.CommunityMainFragment;
import com.bluebird.inhak.woninfo.Dictionary.DictionaryMainFragment;
import com.bluebird.inhak.woninfo.Home.HomeMainFragment;
import com.bluebird.inhak.woninfo.Dictionary.A16Fragment.A16Fragment;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static Context mainContext;
    private DBOpenHelper dbOpenHelper;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    static int FRAGMENT_STATE = 0;
    static int COMMUNITY_PAGE=1;
    static int DICTIONARY_PAGE=2;
    static int HOME_PAGE=3;
    static int CUSTOM_PAGE=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mainContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        replaceNavigation();
        //TODO 로그인 버튼이랑 로그아웃 버튼 <- 실행시 바로 실행되도록 수정필요함


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //급식 푸쉬알림 설정
        SharedPreferences preferences = getSharedPreferences(getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
        boolean pushState = preferences.getBoolean(getString(R.string.shared_food_push_state),true);
        if(pushState) {
            new A16Fragment().setMenu(this);    //메뉴 불러오기
            Log.d("test001","셋 메뉴");
            new A16Fragment.PushAlarm(getApplicationContext()).Alarm(); //푸쉬알림설정
        }


        setBottomBar();

        // 메인화면
        Fragment mainFragment = new HomeMainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container,mainFragment)
                .commit();

        //웹뷰화면
        Fragment webViewFragment = new WebViewFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.webview_fragment_container, webViewFragment)
                .commit();
        //db초기화
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelper.open();
        dbOpenHelper.close();
    }

    @Override
    public void onBackPressed() {
        //왼쪽 메뉴창이 열려있을 경우
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        //웹뷰(원광대학교 웹정보창) 열려있을 경우
        else if(WebViewFragment.WEBVIEW_STATE_OPENED == true)
        {
            WebView webView = (WebView)findViewById(R.id.webview);
            if(webView.canGoBack())//웹뷰 뒤로가기
            {
                webView.goBack();
            }else {
                //WebViewFragment.changeState();
            }
        }
        //프레그먼트(ViewFragment)가 열려있을 경우
        else if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            this.getSupportActionBar().setTitle(R.string.app_name);
            super.onBackPressed();
        }
        else {
            AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
            d.setTitle("종료여부");
            d.setMessage("종료합니다.");
            d.setIcon(R.drawable.ic_alert_exit);

            d.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            });
            d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            d.show();
        }
    }

    public void replaceFragment()
    {
        DictionaryMainFragment menuDictionaryFragment = new DictionaryMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, menuDictionaryFragment);
        fragmentTransaction.commit();
    }
    public void replaceNavigation()
    {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        navigationView.getMenu().clear();
                        if(UserManager.checkLoggedin() == true)
                        {

                        //    View main_view = (View)findViewById(R.id.snackbar_view);
                        //    Snackbar snackbar = Snackbar.make(main_view,"로그인 성공",Snackbar.LENGTH_SHORT);
                        //    View snackBarView = snackbar.getView();
                        //    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                        //    snackbar.show();
                            navigationView.inflateHeaderView(R.layout.nav_header_loggedin);
                            navigationView.inflateMenu(R.menu.nav_menu_loggedin);
                        }else
                        {
                        //    View main_view = (View)findViewById(R.id.snackbar_view);
                        //    Snackbar snackbar = Snackbar.make(main_view,"아이디/패스워드를 확인해주세요",Snackbar.LENGTH_SHORT);
                        //    View snackBarView = snackbar.getView();
                        //    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                        //    snackbar.show();

                            navigationView.inflateHeaderView(R.layout.nav_header_loggedout);
                            //navigationView.inflateMenu(R.menu.nav_menu_loggedout);
                            navigationView.inflateMenu(R.menu.nav_menu_loggedout);

                            Button btn_login = (Button)navigationView.getHeaderView(1).findViewById(R.id.login_btn_login);
                            btn_login.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
                                }
                            });
                        }
                        navigationView.removeHeaderView(navigationView.getHeaderView(0));
                    }
                });
            }
        }).start();
    }


    //네비게이션 아이템 선택 (Drawable View)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //TOAST
        if (id == R.id.nav_createUser) {
            startActivity(new Intent(getApplicationContext(), CreateUserPopup.class));
        } else if (id == R.id.nav_update) {
            navUpdate();
        } else if (id == R.id.nav_tutorial) {
            navTutorial();
        } else if (id == R.id.nav_manage) {
            UserManager.checkLoggedin();
            View main_view = (View)findViewById(R.id.snackbar_view);
            Snackbar snackbar = Snackbar.make(main_view, "미개발 기능입니다.", Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
            snackbar.show();
        } else if (id == R.id.nav_share) {
            navShareKakao();
        } else if (id == R.id.nav_send) {
            Uri uri = Uri.parse("mailto:ij3512@naver.com");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(it);
            View main_view = (View)findViewById(R.id.snackbar_view);
            Snackbar snackbar = Snackbar.make(main_view, "사랑합니다", Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
            snackbar.show();
        }
        else if(id==R.id.nav_logout){
            startActivity(new Intent(getApplicationContext(), PopupLogout.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    public void navUpdate()
    {
        //업데이트 체크 쓰레드
        final HandlerThread handlerThread = new HandlerThread("android_handler");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String store_version = MarketVersion.getMarketVersion(getPackageName());

                String device_version = "";
                try {
                    device_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


                String str[] = device_version.split("\\.");
                if(str.length > 2)
                    device_version = str[0]+ "."+ str[1];
                str = store_version.split("\\.");
                if(str.length > 2)
                    store_version = str[0]+ "."+ str[1];

                //TODO device_version, store_version Checker   버전 관리 코드  Log.d(test)
                Log.d("test","device_version : "+device_version);
                Log.d("test","store_version : "+store_version);

                if (store_version.compareTo(device_version) > 0) {
                    // 업데이트 필요
                    AlertDialog.Builder dialog = new AlertDialog.Builder( MainActivity.this );
                    dialog  .setTitle("업데이트 알림")
                            .setMessage("새로운 내용이 추가됩니다.")
                            .setIcon(R.drawable.ic_menu_update)
                            .setPositiveButton("업데이트", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                                }
                            })
                            .setNeutralButton("취소", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                } else {
                    View main_view = (View)findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "새로운 업데이트 없음", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }
            }
        });
    }
    public void navTutorial()
    {
        //앱이 처음 시작될 때, 튜토리얼 페이지 출력
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        try {
            Class t = Class.forName("com.bluebird.inhak.woninfo."+ "Tutorial" +"Fragment");
            Fragment fragment = (Fragment)t.newInstance();

            fragmentTransaction.replace(R.id.main_fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }catch(Exception e) {}
    }
    public void navShareKakao()
    {
        try {
            KakaoLink kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());
            KakaoTalkLinkMessageBuilder messageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            //final String url = "http://k.kakaocdn.net/14/dn/btqj5sEE60o/5kImSyFQf4hb59CFfgWaO1/o.jpg";
            final String url = "https://lh3.googleusercontent.com/sNwsvcydWs9jKl3cXTYJG_vc_-uVX2aOO1ENmvyeBnvTzU4b3dZSjBDkiIBrZlm1Uw=h150-rw";
            messageBuilder.addImage(url, 1024, 500);
            messageBuilder.addText("학생들을 편리하게 ＜WonInfo＞\n" +
                    "━ 학교사이트\n━ 학과사이트\n━ 웹정보서비스");
            messageBuilder.addAppButton("원광대학교 사전");

            kakaoLink.sendMessage(messageBuilder,getApplicationContext());
        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }

    //bottom_bar 하단바 설정
    private void setBottomBar() {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.bottom_bar_menu_community:
                        if (FRAGMENT_STATE != COMMUNITY_PAGE) {
                            fragment = new CommunityMainFragment();
                            FRAGMENT_STATE = COMMUNITY_PAGE;
                        }
                        break;
                    case R.id.bottom_bar_menu_dictionary:
                        if (FRAGMENT_STATE != DICTIONARY_PAGE) {
                            fragment = new DictionaryMainFragment();
                            FRAGMENT_STATE = DICTIONARY_PAGE;
                        }
                        break;
                    case R.id.bottom_bar_menu_home:
                        if (FRAGMENT_STATE != HOME_PAGE) {
                            fragment = new HomeMainFragment();
                            FRAGMENT_STATE = HOME_PAGE;
                        }
                        break;
                    case R.id.bottom_bar_menu_custom:
                        if (FRAGMENT_STATE != CUSTOM_PAGE) {
                            fragment = new A16Fragment();
                            FRAGMENT_STATE = CUSTOM_PAGE;
                        }
                        break;
                    case R.id.bottom_bar_menu_web:
                        WebViewFragment.changedWebView();
                        return true;
                }
                loadFragment(fragment);
                WebViewFragment.closedWebView();
                return true;
            }
        });
    }


    private boolean loadFragment(Fragment fragment)
    {
        //switching fragment
        if(fragment != null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
