package com.example.jason.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jason on 2017/7/9.
 */

public class CampusTour {
    private ArrayList<Location> locationList = new ArrayList<Location>();
    private DataBase database;

    public CampusTour(DataBase database){
        this.database = database;

    }
    public ArrayList<Location> getLocationList(double currentLongitude, double currentLatitude){
        locationList = database.queryLocation(currentLongitude, currentLatitude, locationList);
        return locationList;
    }
    public void showLocationNow(ArrayList<Location> locationList, GoogleMap mMap, MapsActivity activity){

        final MapsActivity mapactivity = activity;
        HashMap<Marker,Bundle> hashmap = new HashMap<Marker,Bundle>();
        for(Location location:locationList){
            System.out.println(location.getLocationName());
            Bundle bundle = new Bundle();
            bundle.putString("locationName",location.getLocationName());
            bundle.putString("locationInfo",location.getLocationInfo());
            bundle.putDouble("locationLongitude",location.getLocationLongitude());
            bundle.putDouble("locationLatitude",location.getLocationLatitude());


            LatLng newLatLng = new LatLng(location.getLocationLatitude(), location.getLocationLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(newLatLng)              // Sets the center of the map to ZINTUN
                    .zoom(12)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            MarkerOptions shortestmarkerOption = new MarkerOptions().position(newLatLng).title(location.getLocationName());
            Marker marker = mMap.addMarker(shortestmarkerOption);
            hashmap.put(marker,bundle);
            mMap.setOnMarkerClickListener(new LocationListener(hashmap,mapactivity));
        }

    }
}
class LocationListener implements GoogleMap.OnMarkerClickListener {
    private HashMap<Marker,Bundle> hashmap;
    private MapsActivity mapactivity;

    public LocationListener(HashMap<Marker,Bundle> hashmap,MapsActivity mapactivity){
        this.hashmap = hashmap;
        this.mapactivity = mapactivity;
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle bundle = hashmap.get(marker);

        Intent intent =new Intent();
        intent.setClass(mapactivity, InformationActivity.class);
        intent.putExtras(bundle);
        mapactivity.startActivity(intent);

        return false;
    }
}
