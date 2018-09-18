package com.bluebird.inhak.woninfo.Community;

public class BoardListItem {
    private String title;
    private String content;
    private int like = 0;
    private int newContent;
    private int image;

    public BoardListItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getLike() {
        return like;
    }

    public int getNewContent() {
        return newContent;
    }

    public int getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setNewContent(int newContent) {
        this.newContent = newContent;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
