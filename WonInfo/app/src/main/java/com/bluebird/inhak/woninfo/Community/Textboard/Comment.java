package com.bluebird.inhak.woninfo.Community.Textboard;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    String writer_uid;
    String writer_id;
    String writer_photoUri;
    String content;
    String date;

    public String getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(String writer_id) {
        this.writer_id = writer_id;
    }

    public void setWriter_uid(String writer_uid) {
        this.writer_uid = writer_uid;
    }

    public void setWriter_photoUri(String writer_photoUri) {
        this.writer_photoUri = writer_photoUri;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getWriter_uid() {
        return writer_uid;
    }

    public String getWriter_photoUri() {
        return writer_photoUri;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public Map<String, Object> getHashMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        map.put("writer_uid", writer_uid);
        map.put("writer_photoUri", writer_photoUri);
        map.put("date", date);
        return map;
    }
}
