package com.bluebird.inhak.woninfo.Message;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bluebird.inhak.woninfo.Community.BoardListItem;
import com.bluebird.inhak.woninfo.R;
import com.bluebird.inhak.woninfo.UserManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageRoomActivity extends AppCompatActivity {
    private AppCompatActivity activity;
    private MessageItem args; //bundle

    public String connectedMessageRoom = null;    //채팅방 생성 여부 (생성되면 채팅방id 저장 [Message->Id])
    Button messageSend;
    boolean messageSend_Tirger = true;
    EditText editText;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView messageRecycler;
    private MessageRoomAdapter messageAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<MessageItem> messageList = new LinkedList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_message_room_activity);
        this.activity = this;

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.user_swipe_messageroom);
        swipeRefreshLayout.setEnabled(false);
        messageSend = (Button)findViewById(R.id.message_btn_commentwrite);

        //뒤로가기 버튼 추가
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //bundle있다면 가져오기
        if(!getIntent().getExtras().isEmpty()) {
            Bundle bundle = getIntent().getExtras();
            args = (MessageItem) bundle.getSerializable("Bundle");

            //채팅방이 있는 경우이면
            if(args.getMessageRoomUid() != null)
            {
                connectedMessageRoom = args.getMessageRoomUid();
            }
            //리스트에서 사람 이름으로 채팅 검색
            //if(args.isAnon() != false && args.getUid() != null)
            //{
            //}
        }
        getMessage();


        messageRecycler = (RecyclerView)findViewById(R.id.user_recycler_messagelist);
        messageRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        messageRecycler.setLayoutManager(mLayoutManager);
        messageAdapter = new MessageRoomAdapter(messageList, activity);
        messageRecycler.setAdapter(messageAdapter);

        editText = (EditText)findViewById(R.id.message_edit_commentwrite);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().trim().length() == 0  || messageSend_Tirger == false)
                {
                    messageSend.setEnabled(false);
                    messageSend.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.Theme_Gray));
                }else
                {
                    messageSend.setEnabled(true);
                    messageSend.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.colorPrimary));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectedMessageRoom == null) {
                    settingMessageRoom(editText.getText().toString());
                }else {
                    sendMessage(editText.getText().toString());
                }
                Log.d("test098", "메시지 보내기");
                editText.setText(null);

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                messageSend.startAnimation(animation);
            }
        });


        searchList("a");
    }

    public void testCreate()
    {
        MessageItem messageItem =  new MessageItem(connectedMessageRoom,"",    "", Timestamp.now(), "상대방 uid", "상대방 닉네임", false);
        Map<String, Object> newArticle = messageItem.getHashMap();

        db.collection("Users").document(UserManager.firebaseUser.getUid())
                .collection("MessageJoin")
                .add(newArticle)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("test098","메세지 보내기 성공");
                    }
                });
    }

    List<String> Chatlist = new ArrayList();
    public void searchList(String userUid)
    {
        db.collection("Users").document(UserManager.firebaseUser.getUid())
                .collection("MessageJoin")
                .whereEqualTo("anon", false)
                .whereEqualTo("uid", userUid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot ds : queryDocumentSnapshots.getDocuments())
                        {
                            connectedMessageRoom = ds.getString("messageRoomUid");
                            getMessage();
                        }
                    }
                });
    }

    public void settingMessageRoom(final String firstMessage)
    {
        messageSend_Tirger = false; //채팅방 생성까지 잠시 막아둠
        Map<String, Object> newArticle = new HashMap<>();
        newArticle.put("User1_uid", UserManager.firebaseUser.getUid()); //자신 uid 입력
        newArticle.put("User2_uid", "bundle_uid");  //상대방 uid 입력
        db.collection("MessageRooms")
                .add(newArticle)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        connectedMessageRoom = documentReference.getId(); //채팅방 이름 설정
                        synchronizeMessage();
                        sendMessage(firstMessage);
                        testCreate();
                        messageSend_Tirger = true;
                    }
                });
    }

    public void sendMessage(String message)
    {
        Map<String, Object> newArticle = new HashMap<>();
        newArticle.put("message", message);

        newArticle.put("date", Timestamp.now());
        newArticle.put("writerUid", UserManager.firebaseUser.getUid());

        db.collection("MessageRooms").document(connectedMessageRoom).collection("Messages")
                .add(newArticle)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("test098","메세지 보내기 성공");
                    }
                });
    }

    //메시지창을 동기화 합니다. 메시지창이 켜진 다음부터의 대화를 출력합니다.
    public void synchronizeMessage()
    {
        db.collection("MessageRooms").document(connectedMessageRoom)
                .collection("Messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("test098", "Listen failed.", e);
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            Log.d("test098", "받아오기 성공 :"+dc.toString());
                            String messageString = dc.getDocument().getString("message");
                            String writerUid = dc.getDocument().getString("writerUid");
                            Date date = dc.getDocument().getDate("date");
                            Format formatter = new SimpleDateFormat("HH:mm");
                            String dateStr = formatter.format(date);

                            MessageItem message = new MessageItem(connectedMessageRoom, null, null, Timestamp.now(), messageString, writerUid, args.isAnon());
                            messageList.add(message);
                            messageAdapter.notifyDataSetChanged();
                            messageRecycler.scrollToPosition(messageAdapter.getItemCount()-1);
                        }
                    }
                });
    }

    //메시지창에 이전 내역을 불러옵니다. 메시지창이 켜질때, 이전의 대화를 불러옵니다.
    public void getMessage()
    {
        db.collection("MessageRooms").document(connectedMessageRoom).collection("Messages")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments() )
                        {
                            String messageString = documentSnapshot.getString("message");
                            String writerUid = documentSnapshot.getString("writerUid");
                            Date date = documentSnapshot.getDate("date");
                            Timestamp timestamp = new Timestamp(date);
                            Log.d("test098","aaaa:"+timestamp.toString());
                            //Format formatter = new SimpleDateFormat("HH:mm");
                            //String dateStr = formatter.format(date);
                            //Log.d("test098", dateStr);

                            MessageItem message = new MessageItem(connectedMessageRoom,null, messageString, timestamp, writerUid, args.getNickname(), args.isAnon());
                            messageList.add(0, message);
                        }
                        messageAdapter.notifyDataSetChanged();
                        messageRecycler.scrollToPosition(messageAdapter.getItemCount()-1);
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
