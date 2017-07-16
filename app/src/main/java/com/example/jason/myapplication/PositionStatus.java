package com.example.jason.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by jason on 2017/6/4.
 */

public class PositionStatus {
    Position currentPosition = new Position();
    Position lastPosition = new Position();
    MapsActivity activity;
    GoogleMap mMap;
    DataBase database;
    CampusTour campusTour;
    public PositionStatus(GoogleMap mMap, MapsActivity activity, DataBase database){
        this.activity = activity;
        this.mMap = mMap;
        this.database=database;
        campusTour = new CampusTour(database);
        double latitude = 25.021918;
        double longitude = 121.535285;
        LatLng newLatLng = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(newLatLng)              // Sets the center of the map to ZINTUN
                .zoom(20)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(newLatLng).title("Marker"));
        currentPosition.setLatitude(latitude);
        currentPosition.setLongitude(longitude);

    }
    public Position getCurrentPosition()
    {
        return currentPosition;
    }

    public void openGPS()
    {
        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        int minTime = 1000;//ms
        int minDist = 1;//meter
        LocationManager locationMgr = (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener(){

            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                double currentLatitude = location.getLatitude();
                double currentLongitude = location.getLongitude();
                LatLng newLatLng = new LatLng(currentLatitude, currentLongitude);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(newLatLng)              // Sets the center of the map to ZINTUN
                        .zoom(13)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.addMarker(new MarkerOptions().position(newLatLng).title("me"));
                lastPosition.setLongitude(currentPosition.getLongitude());
                lastPosition.setLatitude(currentPosition.getLatitude());
                currentPosition.setLatitude(currentLatitude);
                currentPosition.setLongitude(currentLongitude);

                ArrayList<com.example.jason.myapplication.Location> locationList = campusTour.getLocationList(currentLongitude, currentLatitude);
                campusTour.showLocationNow(locationList,mMap,activity);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDist, (android.location.LocationListener) locationListener);

    }
}
