package com.example.jason.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        TextView textViewPosition = (TextView) findViewById(R.id.textViewPosition);
        TextView textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        Bundle bundle =this.getIntent().getExtras();
        textViewTitle.setText(bundle.get("locationName").toString());
        textViewPosition.setText("緯度： " + bundle.get("locationLatitude")+"\n\n"+"經度： " + bundle.get("locationLongitude"));
        textViewInfo.setText("介紹： " + bundle.get("locationInfo"));
    }

}
