package com.example.jason.myapplication;

/**
 * Created by jason on 2017/7/9.
 */

public class Location {
    private String locationName;
    private String locationImage;
    private String locationInfo;
    private double locationLongitude;
    private double locationLatitude;

    public Location(String locationName, String locationInfo, double locationLongitude, double locationLatitude){
        this.locationName = locationName;
        this.locationInfo = locationInfo;
        this.locationLongitude = locationLongitude;
        this.locationLatitude = locationLatitude;
    }
    public String getLocationName(){
        return locationName;
    }
    public String getLocationInfo(){
        return locationInfo;
    }
    public double getLocationLongitude(){
        return locationLongitude;
    }
    public double getLocationLatitude(){
        return locationLatitude;
    }

}
