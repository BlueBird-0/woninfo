package com.bluebird.inhak.woninfo.Dictionary;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.DBOpenHelper;
import com.bluebird.inhak.woninfo.Dictionary.ListAdapter;
import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

import static com.bluebird.inhak.woninfo.MainActivity.mainContext;

/**
 * Created by InHak on 2017-12-31.
 */

public class DictionaryMainFragment extends Fragment {
    static View view;
    private FragmentActivity fragmentActivity;
    private DBOpenHelper dbOpenHelper;
    private Cursor cursor;
    private AdapterView.OnItemClickListener mOnItemClickListner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dictionary_main_fragment, container, false);
        setHasOptionsMenu(true);
        final ListView listView = (ListView)view.findViewById(R.id.dictionary_listview);
        ListAdapter menuAdapter = new ListAdapter((MainActivity)getActivity());

        //DB에서 메뉴목록 가져오기
        dbOpenHelper = new DBOpenHelper(getActivity());
        dbOpenHelper.open();
        //cursor = dbOpenHelper.sqLiteDatabase.rawQuery("select * from info", null);
        cursor = dbOpenHelper.sqLiteDatabase.rawQuery("select * from info", null);

        while(cursor.moveToNext()){
            if(cursor.getString(2).equals("1"))
            menuAdapter.addItem(cursor.getString(0), cursor.getString(1),cursor.getString(2));
        }
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            if(cursor.getString(2).equals("0"))
            menuAdapter.addItem(cursor.getString(0), cursor.getString(1),cursor.getString(2));
        }



        listView.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        fragmentActivity = (FragmentActivity) context;
        Log.d("qwe", "붙음");
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        Log.d("qwe", "떨어짐");
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar_menu_community_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setQueryHint("원광사전에서 검색합니다...");    //검색 쿼리 힌트
        /* searchView 생김새 결정 코드 부분 */

        //검색창 열릴때와 닫힐때
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Fragment fragment = (Fragment) fragmentActivity.getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                //프레그먼트가 (Dictionary) 꺼져있을시
                if(fragment != null)
                {
                    //다른 프레그먼트일 경우 = 검색 취소
                    try {
                        ((DictionaryMainFragment) fragment).search();
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

                Fragment fragment = (Fragment)fragmentActivity.getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);

                //프레그먼트가 (Dictionary) 꺼져있을시
                if(fragment != null)
                {
                    //다른 프레그먼트일 경우 = 뒤로가기 한번 후, 재검색 실행
                    try {
                        ((DictionaryMainFragment) fragment).search(query);
                    } catch (ClassCastException e) {

                        //onBackPressed();
                        searchView.setQuery(query, true);
                    }
                }
                //프레그먼트가 (Dictionary) 꺼져있을시
                else
                {
                    Log.d("test001", "d");
                    //프레그먼트 (Dictionary) 실행
                    FragmentManager fm = fragmentActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.add(R.id.main_fragment_container, new DictionaryMainFragment());
                    fragmentTransaction.addToBackStack("menu_dictionary");
                    fragmentTransaction.commit();

                    //프레그먼트 실행 후에 한번더 검색하도록
                    new Thread(new Runnable() {
                        @Override
                        public void run()
                        {
                            /*
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(200);
                                    }catch(Exception e){e.printStackTrace();}
                                    //searchView.setQuery( query, true);
                                    DictionaryMainFragment menuDictionaryFragment = (DictionaryMainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                                    menuDictionaryFragment.search(query);
                                }
                            });*/
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

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void search()
    {
        ListView listView = (ListView)view.findViewById(R.id.dictionary_listview);
        ListAdapter menuAdapter = (ListAdapter)listView.getAdapter();


        dbOpenHelper.open();
        //검색어 쿼리
        Cursor cursor = dbOpenHelper.sqLiteDatabase.rawQuery("select * from info", null);

        menuAdapter.clear();
        while(cursor.moveToNext()){
            //... DB 작업 처리
            menuAdapter.addItem(cursor.getString(0), cursor.getString(1),cursor.getString(2));
            menuAdapter.notifyDataSetChanged();
        }
    }


    public void search(String query)
    {
                ListView listView = (ListView)view.findViewById(R.id.dictionary_listview);
                ListAdapter menuAdapter = (ListAdapter)listView.getAdapter();

                dbOpenHelper.open();
                //검색어 쿼리
                String sql = "select * from info where title like '%"+ query + "%' or content like '%" + query + "%';";
                Cursor cursor = dbOpenHelper.sqLiteDatabase.rawQuery(sql,null);
                if(cursor.getCount() == 0)
                {
                    View main_view = (View)getView().getRootView().findViewById(R.id.snackbar_view);
                    Snackbar snackbar = Snackbar.make(main_view, "검색된 값이 없습니다.", Snackbar.LENGTH_SHORT);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(ContextCompat.getColor(mainContext,R.color.Theme_Blue));
                    snackbar.show();
                }else {
                    menuAdapter.clear();
                    while (cursor.moveToNext()) {
                        //... DB 작업 처리
                        menuAdapter.addItem(cursor.getString(0), cursor.getString(1),cursor.getString(2));
                    }
                    menuAdapter.notifyDataSetChanged();
                }
    }

}

