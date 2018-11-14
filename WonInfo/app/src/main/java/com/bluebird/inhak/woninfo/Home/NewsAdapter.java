package com.bluebird.inhak.woninfo.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bluebird.inhak.woninfo.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class NewsAdapter extends BaseAdapter {
    private List articleList;
    private Context context;

    public NewsAdapter(List list, Context context)
    {
        this.articleList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.home_main_list_item, parent, false);



        TextView board = (TextView)convertView.findViewById(R.id.home_main_text_broadname1);
        TextView title = (TextView)convertView.findViewById(R.id.home_main_text_title);

        NewsItem newsItem = (NewsItem) articleList.get( position );
        board.setText(newsItem.getBoard());
        title.setText(newsItem.getTitle());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
