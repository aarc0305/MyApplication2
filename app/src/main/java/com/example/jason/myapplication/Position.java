package com.example.jason.myapplication;

/**
 * Created by jason on 2017/6/4.
 */

public class Position {
    private double longitude;
    private double latitude;
    public Position(){

    }
    public double getLongitude()
    {
        return longitude;
    }
    public double getLatitude()
    {
        return latitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
}
