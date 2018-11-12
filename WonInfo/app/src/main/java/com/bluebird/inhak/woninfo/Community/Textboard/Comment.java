package com.bluebird.inhak.woninfo.Community.Textboard;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    String writer_uid;
    String writer_photoUri;
    String content;

    public void setWriter_uid(String writer_uid) {
        this.writer_uid = writer_uid;
    }

    public void setWriter_photoUri(String writer_photoUri) {
        this.writer_photoUri = writer_photoUri;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter_uid() {

        return writer_uid;
    }

    public String getWriter_photoUri() {
        return writer_photoUri;
    }

    public String getContent() {
        return content;
    }

    public Map<String, Object> getHashMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        map.put("writer_uid", writer_uid);
        map.put("writer_photoUri", writer_photoUri);
        return map;
    }
}
