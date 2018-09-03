package com.bluebird.inhak.woninfo.Dictionary.A15Fragment;

/**
 * Created by InHak on 2018-02-17.
 */

public class A15Item {
    private String date;
    private String content;

    public String getDate()    { return this.date; }
    public String getContent()    { return this.content; }
    public void setDate(String date)    { this.date = date; }
    public void setContent(String content)    { this.content = content; }

    public A15Item(String date, String content)
    {
        this.date = date;
        this.content = content;
    }
}
