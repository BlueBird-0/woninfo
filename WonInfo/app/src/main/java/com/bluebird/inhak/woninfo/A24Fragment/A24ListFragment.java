package com.bluebird.inhak.woninfo.A24Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bluebird.inhak.woninfo.R;

import java.util.ArrayList;

/**
 * Created by InHak on 2017-12-31.
 */

public class A24ListFragment extends Fragment {
    ArrayList<String> leftList;     //등교리스트
    ArrayList<String> rightList;    //하교리스트

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_24_list_fragment, container, false);

        ListView leftList = (ListView)view.findViewById(R.id.a_24_leftlist);
        ListView rightList = (ListView)view.findViewById(R.id.a_24_rightlist);
        ArrayList<A24Item> leftData = new ArrayList<>();
        ArrayList<A24Item> rightData = new ArrayList<>();


        Log.d("test001", getArguments().getString("area"));

        A24Item test = new A24Item("삼천동", "07:10");
        leftData.add(test);
        leftData.add(test);
        leftData.add(test);
        leftData.add(test);
        leftData.add(test);
        leftData.add(test);
        A24Adapter adapter = new A24Adapter(view.getContext(), R.layout.a_24_list_item, leftData);
        leftList.setAdapter(adapter);
        A24Adapter.setListViewHeightBasedOnChildren(leftList);




        A24Item test2 = new A24Item("삼천동", "07:10");
        rightData.add(test2);
        rightData.add(test2);
        rightData.add(test2);
        A24Adapter adapter2 = new A24Adapter(view.getContext(), R.layout.a_24_list_item, rightData);
        rightList.setAdapter(adapter2);
        A24Adapter.setListViewHeightBasedOnChildren(rightList);

        return view;
    }
}
