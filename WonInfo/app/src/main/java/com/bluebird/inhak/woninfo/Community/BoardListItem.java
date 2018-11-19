package com.bluebird.inhak.woninfo.Community;

import android.widget.GridLayout;

import java.io.Serializable;

public class BoardListItem implements Serializable {
    private String documentId;
    private double num;
    private String id;
    private String uid;
    private String price;
    private String kinds;
    private String board;


    private String title;
    private String content;
    private String date;
    private double imageCount;
    private double likeCount;
    private double commentCount;

    public BoardListItem() {
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public BoardListItem(String title, String content, double num) {
        this.title = title;
        this.content = content;
        this.num = num;
        this.board = board;

        // null
        this.documentId = "null";
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

    public String getKinds(){
        return kinds;
    }
    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public String getPrice(){
        return price;
    }
    public void setPrice(String price){
        this.price = price;
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
