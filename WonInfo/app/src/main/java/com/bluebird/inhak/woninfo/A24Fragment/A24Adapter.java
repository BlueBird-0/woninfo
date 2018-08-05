package com.bluebird.inhak.woninfo.A24Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

/**
 * Created by InHak on 2018-02-17.
 */

public class A24Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<A24Item> data;
    private int layout;

    public A24Adapter(Context context, int layout, ArrayList<A24Item> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }

    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position) {//return data.get(position).getName();}
        return "";
    }
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        A24Item listviewitem=data.get(position);
        TextView date=(TextView)convertView.findViewById(R.id.a_24_departure_area);
        date.setText(listviewitem.getDepartureArea());

        TextView content=(TextView)convertView.findViewById(R.id.a_24_departure_time);
        content.setText(listviewitem.getDepartureTime());

        RelativeLayout busList = convertView.findViewById(R.id.a_24_busList_btn);
        busList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //시간과 제목 보내주면서 프레그먼트 출력
            }
        });
        return convertView;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
