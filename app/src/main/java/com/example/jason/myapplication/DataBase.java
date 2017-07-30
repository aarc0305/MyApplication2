package com.example.jason.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jason on 2017/5/30.
 */

public class DataBase {
    private SQLiteDatabase sqliteDB;
    public DataBase(SQLiteDatabase sqliteDB){
        this.sqliteDB = sqliteDB;
        String createLocationTable = "CREATE TABLE location_table(_id INTEGER PRIMARY KEY , locationName TEXT,locationImage TEXT,locationInfo TEXT, locationLongitude REAL, locationLatitude REAL)";

        String createMemberTable = "CREATE TABLE member_table(_id INTEGER PRIMARY KEY , account TEXT,password TEXT)";
        try{
            this.sqliteDB.execSQL(createLocationTable);
            this.sqliteDB.execSQL(createMemberTable);
        }
        catch(Exception e){

        }

    }
    public boolean queryMember (String account,String password){
        System.out.println("1");
        Cursor query = sqliteDB.query("member_table", new String[] {"account", "password"}, "account='"+account+"'", null, null, null, null, null);
        System.out.println("2");
        int row_num = query.getCount();
        System.out.println(row_num);
        if(row_num > 0){
            query.moveToFirst();

            String getAccount = query.getString(0);
            String getPassword = query.getString(1);
            System.out.println(getAccount);
            System.out.println(getPassword);
            if(getPassword.equals(password) && getAccount.equals(account)){
                return true;
            }
            else{
                return false;
            }

        }
        return false;

    }

    public void insertMember (String account, String password){
        //sqliteDB.execSQL("DROP TABLE member_table");
        try{
            Cursor query = sqliteDB.query("member_table", new String[] {"account", "password"}, null, null, null, null, null, null);
        }
        catch (Exception e){
            String createTable01 = "CREATE TABLE member_table(_id INTEGER PRIMARY KEY , account TEXT,password TEXT)";
            sqliteDB.execSQL(createTable01);
        }
        String insert_1 = "INSERT INTO member_table (account,password) values ('"+account+"','"+password+"')";
        sqliteDB.execSQL(insert_1);
    }

    public ArrayList<Location> queryLocation (double currentLongitude, double currentLatitude, ArrayList<Location> locationList){
        String condition = "locationLongitude<'"+(currentLongitude+0.05)+"'and locationLongitude>'"+(currentLongitude-0.05)+"'and locationLatitude<'"+(currentLatitude+0.05)+"'and locationLatitude>'"+(currentLatitude-0.05)+"'";
        Cursor query = sqliteDB.query("location_table", new String[] {"locationName", "locationInfo", "locationLongitude", "locationLatitude"}, condition, null, null, null, null, null);
        //Cursor query = sqliteDB.query("location_table", new String[] {"locationName", "locationInfo", "locationLongitude", "locationLatitude"}, null, null, null, null, null, null);
        int row_num = query.getCount();
        System.out.println(row_num);
        double shortestDis  = 1000000000;
        query.moveToFirst();
        Location shortestLocation = null;
        PriorityQueue<Double> queue = new PriorityQueue<Double>();
        HashMap<Double,Location> map = new HashMap<Double,Location>();
        for(int i=0;i<row_num;i++){
            String getLocationName = query.getString(0);
            String getLocationInfo = query.getString(1);
            System.out.println(getLocationName);
            System.out.println(getLocationInfo);
            double getLocationLongitude = query.getDouble(2);
            double getLocationLatitude = query.getDouble(3);
            double distance = getDistance(currentLongitude,currentLatitude,getLocationLongitude,getLocationLatitude);
            /*if(distance<shortestDis){
                shortestDis = distance;

                shortestLocation = new Location(getLocationName, getLocationInfo, getLocationLongitude, getLocationLatitude);

            }*/
            queue.add(distance);
            System.out.println(distance+getLocationName);
            map.put(distance,new Location(getLocationName, getLocationInfo, getLocationLongitude, getLocationLatitude));
            query.moveToNext();
        }
        /*System.out.println(shortestLocation.getLocationLatitude());
        System.out.println(shortestLocation.getLocationLongitude());
        locationList.add(shortestLocation);*/
        if(queue.size()>=3){
            for(int i=0;i<3;i++){
                double shortDis = queue.peek();
                Location closelocation = map.get(shortDis);
                locationList.add(closelocation);
                queue.remove(shortDis);
            }
        }
        else{
            int size = queue.size();
            for(int i=0;i<size;i++){
                double shortDis = queue.peek();
                Location closelocation = map.get(shortDis);
                System.out.println(i+": "+closelocation.getLocationName());
                locationList.add(closelocation);
                queue.remove(shortDis);
            }
        }

        return locationList;


    }
    public double getDistance(double longitude1, double latitude1, double longitude2,double latitude2)
    {
        double radLatitude1 = latitude1 * Math.PI / 180;
        double radLatitude2 = latitude2 * Math.PI / 180;
        double l = radLatitude1 - radLatitude2;
        double p = longitude1 * Math.PI / 180 - longitude2 * Math.PI / 180;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                * Math.pow(Math.sin(p / 2), 2)));
        distance = distance * 6378137.0;
        distance = Math.round(distance * 10000) / 10000;

        return distance ;
    }
    public void insertLocation (String locationName, String locationImage, String locationInfo, double locationLongitude, double locationLatitude){
        //sqliteDB.execSQL("DROP TABLE location_table");

        try{
            Cursor query = sqliteDB.query("location_table", new String[] {"locationName", "locationImage", "locationInfo", "locationLongitude", "locationLatitude"}, null, null, null, null, null, null);
        }
        catch (Exception e){
            String createTable01 = "CREATE TABLE location_table(_id INTEGER PRIMARY KEY , locationName TEXT,locationImage TEXT,locationInfo TEXT, locationLongitude REAL, locationLatitude REAL)";
            sqliteDB.execSQL(createTable01);
        }
        System.out.println("存入景點");
        Cursor query = sqliteDB.query("location_table", new String[] {"locationName", "locationImage", "locationInfo", "locationLongitude", "locationLatitude"}, "locationName='"+locationName+"'", null, null, null, null, null);

        if(query.getCount()==0){
            String insert_1 = "INSERT INTO location_table (locationName,locationImage,locationInfo,locationLongitude,locationLatitude) values ('"+locationName+"','"+locationImage+"','"+locationInfo+"','"+locationLongitude+"','"+locationLatitude+"')";
            sqliteDB.execSQL(insert_1);
            System.out.println(locationName+"本來不在");
        }
        System.out.println(locationName+"原本存在");



        //String insert_1 = "INSERT INTO location_table (locationName,locationImage,locationInfo,locationLongitude,locationLatitude) values ('"+locationName+"','"+locationImage+"','"+locationInfo+"','"+locationLongitude+"','"+locationLatitude+"')";
        //sqliteDB.execSQL(insert_1);
    }
}
