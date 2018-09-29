package com.bluebird.inhak.woninfo.Dictionary;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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

/**
 * Created by InHak on 2017-12-31.
 */

public class DictionaryMainFragment extends Fragment {
    static View view;
    private DBOpenHelper dbOpenHelper;
    private Cursor cursor;
    private AdapterView.OnItemClickListener mOnItemClickListner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dictionary_main_fragment, container, false);
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
                    Toast.makeText(view.getContext(), "검색된 값이 없습니다.", Toast.LENGTH_SHORT).show();
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

