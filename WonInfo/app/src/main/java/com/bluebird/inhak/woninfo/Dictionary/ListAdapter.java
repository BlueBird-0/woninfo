package com.bluebird.inhak.woninfo.Dictionary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

/**
 * Created by InHak on 2018-01-28.
 */

public class ListAdapter extends BaseAdapter {

    private ArrayList<ListItem> arrayList = new ArrayList<>();
    private MainActivity mainActivity;
    private int count=0;    //listView 색상 나타내기 위해서 이용 -> 나중에는 분류별로 색상을 나타내야함

    public ListAdapter(MainActivity mainActivity) {
        super();
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public ListItem getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        /*'item_menu' Layout을 inflate하여 convertView 참조 획득 */
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_menu, parent, false);
        }
        /* 'item_menu' 에 정의된 위젯에 대한 참조 획득 */
        RelativeLayout menuList_btn = (RelativeLayout) convertView.findViewById(R.id.menuList_btn);
        TextView menuList_title = (TextView) convertView.findViewById(R.id.menuList_title);
        ///////////////////////////////////////////////////////aslkdasnldkandklsandklsandlksandklsadnklsadnlsakdnsakldnsakldnakldnlksadnklsa//////asdklasnbdlkasnfsalkfnsaklnal k즐겨찾기 추가해야함



        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final ListItem  menuItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        final String primaryKey = menuItem.getPrimaryKey();

        menuList_title.setText(menuItem.getText());
        /////////////////////////////////////////////////////////////asdklsndsaklndlsakdnsalkdnsakldnsakldnsa;kadsnglkdfn;kldfngbkl'adfnbl'akdfbnl'akdfbasl;kdnaslasknlksanslanlsansfkl 즐겨찾기 준비
        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면 된다) */
        menuList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                try {
                    Class t = Class.forName("com.bluebird.inhak.woninfo.Dictionary."+ primaryKey +"Fragment."+ primaryKey +"Fragment");
                    //Class t = Class.forName("com.example.inhak.woninfo."+"A09"+"Fragment"+".A09"+"Fragment");
                    Fragment fragment = (Fragment)t.newInstance();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_open, 0, 0, R.anim.slide_close);
                    fragmentTransaction.add(R.id.main_fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    mainActivity.getSupportActionBar().setTitle(menuItem.getText());
                }catch(Exception e) {}
            }
        });
        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성*/
    public void addItem(String primaryKey, String text,String likeCheck)
    {
        Boolean likeChange=Boolean.valueOf(likeCheck);
        ListItem menuItem = new ListItem();

        /* MenuItem에 아이템을 setting 한다. */
        menuItem.setPrimaryKey(primaryKey);
        menuItem.setText(text);
        menuItem.setLike(likeChange);

        arrayList.add(menuItem);
    }

    public void clear()
    {
        arrayList.clear();
    }

}
