package com.bluebird.inhak.woninfo;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by InHak on 2017-12-31.
 */

public class MenuDictionaryFragment extends Fragment {
    static View view;
    private DBOpenHelper dbOpenHelper;
    private Cursor cursor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_dictionary_fragment, container, false);
        ListView listView = (ListView)view.findViewById(R.id.listview);
        MenuAdapter menuAdapter = new MenuAdapter((MainActivity)getActivity());

        //DB에서 메뉴목록 가져오기
        dbOpenHelper = new DBOpenHelper(getActivity());
        dbOpenHelper.open();
        cursor = dbOpenHelper.sqLiteDatabase.rawQuery("select * from info", null);

        while(cursor.moveToNext()){
            menuAdapter.addItem(cursor.getString(0), cursor.getString(1));
        }
        listView.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
        return view;
    }


    public void search()
    {
        ListView listView = (ListView)view.findViewById(R.id.listview);
        MenuAdapter menuAdapter = (MenuAdapter)listView.getAdapter();

        dbOpenHelper.open();
        //검색어 쿼리
        Cursor cursor = dbOpenHelper.sqLiteDatabase.rawQuery("select * from info", null);

        menuAdapter.clear();
        while(cursor.moveToNext()){
            //... DB 작업 처리
            menuAdapter.addItem(cursor.getString(0), cursor.getString(1));
            menuAdapter.notifyDataSetChanged();
        }
    }


    public void search(String query)
    {
                ListView listView = (ListView)view.findViewById(R.id.listview);
                MenuAdapter menuAdapter = (MenuAdapter)listView.getAdapter();

                dbOpenHelper.open();
                //검색어 쿼리
                String sql = "select * from info where title like '%"+ query + "%' or content like '%" + query + "%';";
                Cursor cursor = dbOpenHelper.sqLiteDatabase.rawQuery(sql,null);
                if(cursor.getCount() == 0)
                {
                    Toast.makeText(view.getContext(), "검색된 값이 없습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    menuAdapter.clear();
                    while (cursor.moveToNext()) {
                        //... DB 작업 처리
                        menuAdapter.addItem(cursor.getString(0), cursor.getString(1));
                    }
                    menuAdapter.notifyDataSetChanged();
                }
    }

}

