package com.bluebird.inhak.woninfo.Dictionary.A24Fragment;

/**
 * Created by InHak on 2018-02-17.
 */

public class A24Item {
    private String departureArea;   //출발장소
    private String departureTime;   //출발시간
    private String transit;         //경유지

    public String getDepartureArea() { return this.departureArea; }
    public String getDepartureTime() { return this.departureTime; }
    public String getTransit() { return this.transit; }
    public void setDepartureArea(String departureArea) { this.departureArea = departureArea; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public void setTransit(String transit) { this.transit = transit; }

    public A24Item(String departureArea, String departureTime, String transit)
    {
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.transit = transit;
    }
    public A24Item(String departureArea, String departureTime)
    {
        this.departureArea = departureArea;
        this.departureTime = departureTime;
        this.transit = null;
    }
}
