package com.bluebird.inhak.woninfo.Message;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageItem implements Serializable {
    private String boardName;
    private String lastMessage;
    private Date date;
    private String nickname;
    private String messageRoomUid;
    private String uid;
    private boolean anon;
    private int alert_num;

    public MessageItem()
    {}
    public MessageItem(String messageRoomUid, String boardName, String lastMessage, Date date, String uid, String nickname, boolean anon)
    {
        this.messageRoomUid = messageRoomUid;
        this.boardName = boardName;
        this.lastMessage = lastMessage;
        this.date = date;
        this.uid = uid;
        this.nickname = nickname;
        this.anon = anon;
        this.alert_num = 0;
    }


    public int getAlert_num() {
        return alert_num;
    }

    public void setAlert_num(int alert_num) {
        this.alert_num = alert_num;
    }

    public Map<String, Object> getHashMap(){
        Map<String, Object> newArticle = new HashMap<>();
        newArticle.put("boardName", "");
        newArticle.put("lastMessage", lastMessage);
        newArticle.put("date", date);
        newArticle.put("nickname", nickname);
        newArticle.put("messageRoomUid", messageRoomUid);
        newArticle.put("uid", uid);
        newArticle.put("anon", anon);
        newArticle.put("alert_num", alert_num);
        return newArticle;
    }

    public String getMessageRoomUid() {
        return messageRoomUid;
    }

    public void setMessageRoomUid(String messageRoomUid) {
        this.messageRoomUid = messageRoomUid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isAnon() {
        return anon;
    }

    public void setAnon(boolean anon) {
        this.anon = anon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
