package com.bluebird.inhak.woninfo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton  fab =(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewFragment.changeState();
            }
        });


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //TODO 로그인 버튼이랑 로그아웃 버튼 <- 실행시 바로 실행되도록 수정필요함
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                //로그인 상황 확인
                final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
                boolean loginState = sharedPref.getBoolean(getString(R.string.shared_user_loginState), false);
                //로그인 돼 있을 경우
                if(loginState == true) {
                    navigationView.inflateHeaderView(R.layout.nav_header_login);
                    navigationView.removeHeaderView( navigationView.getHeaderView(0));

                    //정보 출력
                    TextView login_text_name = (TextView)findViewById(R.id.login_text_name);
                    login_text_name.setText( sharedPref.getString(getString(R.string.shared_user_name), "") );
                    TextView login_text_grade = (TextView)findViewById(R.id.login_text_grade);
                    login_text_grade.setText( sharedPref.getString(getString(R.string.shared_user_grade), "") + "학년");
                    TextView login_text_major = (TextView)findViewById(R.id.login_text_major);
                    login_text_major.setText( sharedPref.getString(getString(R.string.shared_user_major), ""));

                    //로그아웃 버튼 클릭시
                    Button btn_logout = (Button)navigationView.getHeaderView(0).findViewById(R.id.login_btn_logout);
                    btn_logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.remove(getString(R.string.shared_user_id));
                            editor.remove(getString(R.string.shared_user_pw));
                            editor.remove(getString(R.string.shared_user_loginState));
                            editor.remove(getString(R.string.shared_user_name));
                            editor.remove(getString(R.string.shared_user_major));
                            editor.remove(getString(R.string.shared_user_grade));
                            editor.remove(getString(R.string.shared_user_dormitory));
                            editor.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        }
                    });
                }else
                {
                    navigationView.inflateHeaderView(R.layout.nav_header_main);
                    navigationView.removeHeaderView( navigationView.getHeaderView(0));
                    //로그인 버튼 클릭시
                    Button btn_login = (Button)navigationView.getHeaderView(0).findViewById(R.id.login_btn_login);
                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
                        }
                    });
                }
                Log.d("test33","호출!");
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //급식 푸쉬알림 설정
        SharedPreferences preferences = getSharedPreferences(getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
        boolean pushState = preferences.getBoolean(getString(R.string.shared_food_push_state),true);
        if(pushState) {
            new A16Fragment().setMenu(this);    //메뉴 불러오기
            new A16Fragment.PushAlarm(getApplicationContext()).Alarm(); //푸쉬알림설정
        }



        //db초기화
        dbOpenHelper = new DBOpenHelper(this);
        dbOpenHelper.open();
        dbOpenHelper.close();
    }
    //TODO 로그인 버튼쓸때 사용할 함수
    public void drawerLayoutRefresh()
    {
        //로그인 상황 확인
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
        boolean loginState = sharedPref.getBoolean(getString(R.string.shared_user_loginState), false);
        //로그인 돼 있을 경우
        if(loginState == true) {
            navigationView.inflateHeaderView(R.layout.nav_header_login);
            navigationView.removeHeaderView( navigationView.getHeaderView(0));
            //로그아웃 버튼 클릭시
            Button btn_logout = (Button)navigationView.getHeaderView(0).findViewById(R.id.login_btn_logout);
            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(getString(R.string.shared_user_loginState));
                    editor.commit();
                }
            });
        }else
        {
            //로그인 버튼 클릭시
            Button btn_login = (Button)navigationView.getHeaderView(0).findViewById(R.id.login_btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
                }
            });
        }
        Log.d("test33","호출!");
    }

    @Override
    public void onBackPressed() {
        //왼쪽 메뉴창이 열려있을 경우
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        //웹뷰(원광대학교 웹정보창) 열려있을 경우
        else if(WebViewFragment.webViewIsOpened())
        {
            WebView webView = (WebView)findViewById(R.id.webview);
            if(webView.canGoBack())//웹뷰 뒤로가기
            {
                webView.goBack();
            }else {
                WebViewFragment.changeState();
            }
        }
        //프레그먼트(ViewFragment)가 열려있을 경우
        else if (getFragmentManager().getBackStackEntryCount() != 0) {
            this.getSupportActionBar().setTitle(R.string.app_name);
            super.onBackPressed();
        }
        else {
            AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
            d.setTitle("종료여부");
            d.setMessage("진짜 갈꺼야? ㅇ3ㅇ;;;");
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
        MenuDictionaryFragment menuDictionaryFragment = new MenuDictionaryFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view_fragment, menuDictionaryFragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView)searchItem.getActionView();
       searchView.setQueryHint(" 원광사전에서 검색합니다...");    //검색 쿼리 힌트
        /* searchView 생김새 결정 코드 부분 */

        //검색창 열릴때와 닫힐때
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.view_fragment);
                //프레그먼트가 (Dictionary) 꺼져있을시
                if(fragment != null)
                {
                    //다른 프레그먼트일 경우 = 검색 취소
                    try {
                        ((MenuDictionaryFragment) fragment).search();
                    }catch(ClassCastException e) { e.getStackTrace(); }
                }
                return true;
            }
        });
        //검색어 입력 처리
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            //검색어 입력시
            @Override
            public boolean onQueryTextSubmit(final String query) {
                Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.view_fragment);
                //프레그먼트가 (Dictionary) 꺼져있을시
                if(fragment != null)
                {
                    //다른 프레그먼트일 경우 = 뒤로가기 한번 후, 재검색 실행
                    try {
                        ((MenuDictionaryFragment) fragment).search(query);
                    }catch(ClassCastException e) {
                        onBackPressed();
                        searchView.setQuery( query, true);
                    }
                }
                //프레그먼트가 (Dictionary) 꺼져있을시
                else
                {
                    //프레그먼트 (Dictionary) 실행
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.add(R.id.view_fragment, new MenuDictionaryFragment());
                    fragmentTransaction.addToBackStack("menu_dictionary");
                    fragmentTransaction.commit();

                    //프레그먼트 실행 후에 한번더 검색하도록
                    new Thread(new Runnable() {
                        @Override
                        public void run()
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(200);
                                    }catch(Exception e){e.printStackTrace();}
                                    //searchView.setQuery( query, true);
                                    MenuDictionaryFragment menuDictionaryFragment = (MenuDictionaryFragment) getFragmentManager().findFragmentById(R.id.view_fragment);
                                    menuDictionaryFragment.search(query);
                                }
                            });
                        }
                    }).start();
                }
                return false;
            }
            //검색어 입력 도중
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Menu option 설정
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //TOAST
        if (id == R.id.nav_camera) {
            Toast.makeText(this, "자퇴하고싶다...", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_update) {
            navUpdate();
        } else if (id == R.id.nav_tutorial) {
            navTutorial();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "미개발 기능입니다", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            navShareKakao();
        } else if (id == R.id.nav_send) {
            Uri uri = Uri.parse("mailto:ij3512@naver.com");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(it);
            Toast.makeText(this, "사랑합니다", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this, "새로운 업데이트 없음", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void navTutorial()
    {
        //앱이 처음 시작될 때, 튜토리얼 페이지 출력
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        try {
            Class t = Class.forName("com.bluebird.inhak.woninfo."+ "Tutorial" +"Fragment");
            Fragment fragment = (Fragment)t.newInstance();

            fragmentTransaction.replace(R.id.view_fragment, fragment);
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

}
