package com.bluebird.inhak.woninfo.Community;

import java.io.Serializable;

public class BoardListItem implements Serializable {
    private String documentId;
    private double num;
    private String profileUri;
    private String id;
    private String uid;

    private String title;
    private String content;
    private String date;
    private double imageCount;
    private double likeCount;
    private double commentCount;

    public BoardListItem() {
    }

    public BoardListItem(String title, String content, double num) {
        this.title = title;
        this.content = content;
        this.num = num;
        // null
        this.documentId = "null";
        this.profileUri = "null";
        this.id = "null";
        this.uid = "null";
        this.date = "null";
        this.imageCount = 0;
        this.likeCount = 0;
        this.commentCount = 0;
    }

    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProfileUri() {
        return profileUri;
    }
    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public double getNum() {
        return num;
    }
    public void setNum(double num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public double getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(double likeCount) { this.likeCount = likeCount; }

    public double getCommentCount() {
        return commentCount;
    }
    public void setCommentCount(double commentCount) { this.commentCount = commentCount; }

    public double getImageCount() {
        return imageCount;
    }
    public void setImageCount(double imageCount) { this.imageCount = imageCount; }

}
