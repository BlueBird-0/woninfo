package com.bluebird.inhak.woninfo.Message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class MessageMainActivity extends AppCompatActivity {
    private AppCompatActivity activity;
    private BoardListItem args; //bundle

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<MessageItem> messageList = new ArrayList();
    RecyclerView messageRecyclerView;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_message_activity);
        this.activity = this;

        //뒤로가기 버튼 추가
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messageRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_list);
        messageAdapter = new MessageAdapter(messageList, activity);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        messageRecyclerView.setLayoutManager(mLayoutManager);
        messageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        messageRecyclerView.setAdapter(messageAdapter);

        synchronizeMessageRooms();
    }

    public void synchronizeMessageRooms()
    {
        db.collection("Users").document(UserManager.firebaseUser.getUid()).collection("MessageJoin")
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("test098", "Listen failed.", e);
                    return;
                }

                messageList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("messageRoomUid") != null) {

                        String messageRoomUid = doc.getString("messageRoomUid");
                        boolean anon = doc.getBoolean("anon");
                        String boardName = doc.getString("boardName");
                        String lastMessage = doc.getString("lastMessage");
                        Date date = doc.getDate("date");
                        Timestamp timestamp = new Timestamp(date);
                        String nickname = doc.getString("nickname");
                        String uid = doc.getString("uid");

                        MessageItem message = new MessageItem(messageRoomUid, boardName, lastMessage, timestamp,uid, nickname, anon);
                        messageList.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
