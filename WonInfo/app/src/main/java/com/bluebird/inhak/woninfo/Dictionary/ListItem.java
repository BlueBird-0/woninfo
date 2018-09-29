package com.bluebird.inhak.woninfo.Dictionary;

/**
 * Created by InHak on 2018-01-28.
 */

public class ListItem {
    private String primaryKey;
    private String title;
    private boolean like;

    public String getPrimaryKey() { return primaryKey; }
    public void setPrimaryKey(String primaryKey)
    {
        this.primaryKey = primaryKey;
    }
    public String getText()
    {
        return title;
    }
    public void setText(String title)
    {
        this.title = title;
    }
    public boolean getLike()
    {
        return like;
    }
    public void setLike (boolean like)
    {
        this.like = like;
    }

}
