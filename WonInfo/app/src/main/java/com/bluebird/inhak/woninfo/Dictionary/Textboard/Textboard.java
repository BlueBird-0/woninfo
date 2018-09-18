package com.bluebird.inhak.woninfo.Dictionary.Textboard;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bluebird.inhak.woninfo.R;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by InHak on 2017-12-31.
 */

public class Textboard extends Fragment {


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.textboard, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return view;
    }
}
