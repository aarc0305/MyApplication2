package com.example.jason.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    DataBase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SQLiteDatabase sqliteDB = openOrCreateDatabase("db2.db", MODE_PRIVATE,null);
        database = new DataBase(sqliteDB);
        database.insertLocation("台大體育館","/","國立臺灣大學綜合體育館（NTU Sports Center，或稱台大巨蛋體育館、台大小巨蛋[1]）是國立臺灣大學總校區的一項設施",121.535285,25.021918);
        database.insertLocation("臺北小巨蛋","/","臺北小巨蛋（英語名稱：Taipei Arena），為臺北市政府的一座多功能體育館，位於中華民國臺北市南京東路及敦化北路口。",121.547988,25.049881);
        database.insertLocation("艋舺龍山寺","/","艋舺龍山寺，也稱萬華龍山寺或臺北龍山寺，簡稱龍山寺，位於臺灣臺北市萬華（此區舊稱艋舺）廣州街211號，是中華民國直轄市定古蹟",121.499832,25.036825);
        database.insertLocation("國立國父紀念館","/","國立國父紀念館（通稱國父紀念館）是為紀念中華民國國父孫中山先生百年誕辰而興建的綜合性文化設施，於1972年5月16日落成啟用，位於臺北東區，緊鄰信義計畫區。",121.560265,25.039550);
        database.insertLocation("中正紀念堂","/","中正紀念堂是一座為紀念故前中華民國總統蔣中正而興建的建築，位於臺北市中正區，也是眾多紀念蔣中正總統的建築中規模最大者。",121.519722,25.035556);
        database.insertLocation("台北市立動物園","/","臺北市立動物園是臺灣臺北市的一座公立動物園，隸屬於臺北市政府教育局。",121.588491,24.996899);
        database.insertLocation("美麗華百樂園","/","美麗華百樂園是一位於台灣台北市中山區的大型綜合用途購物中心，營業面積25000坪",121.556949,25.083430);
        database.insertLocation("圓山公園","/","圓山公園，位於台灣台北市中山區，捷運圓山站與美術公園之間，在2010年臺北國際花卉博覽會期間劃入展區，目前為花博公園的一部分。",121.522119,25.071703);
        database.insertLocation("饒河街觀光夜市","/","饒河街觀光夜市，又稱饒河街夜市、饒河夜市。街名源自合江省饒河縣，滿語意為「禽獸眾多之地」。",121.57463,25.0503576);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        PositionStatus positionStatus = new PositionStatus(mMap,this,database);
        positionStatus.openGPS();

    }

}
