package com.example.jason.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText inputAccount;
    private EditText inputPassword;
    private Button loginButton;
    private Button signupButton;
    private TextView disPlay;

    private MemberManager memberManager;
    SQLiteDatabase sqliteDB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        inputAccount = (EditText) findViewById(R.id.inputAccount);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);
        disPlay = (TextView) findViewById(R.id.disPlay);

        sqliteDB = openOrCreateDatabase("db1.db", MODE_PRIVATE,null);
        DataBase database = new DataBase(sqliteDB);
        //database.insertMember("jason","123");

        memberManager = new MemberManager(database);

        loginButton.setOnClickListener(loginDoListener);
        signupButton.setOnClickListener(signupDoListener);



    }
    private Button.OnClickListener loginDoListener = new Button.OnClickListener(){
        public void onClick(View v){
            String inputAccountString = inputAccount.getText().toString();
            String inputPasswordString = inputPassword.getText().toString();
            if(memberManager.login(inputAccountString,inputPasswordString)){
                disPlay.setText("success");
                System.out.println("success");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
            else{
                disPlay.setText("fail");
                System.out.println("fail");
            }
        }
    };
    private Button.OnClickListener signupDoListener = new Button.OnClickListener(){
        public void onClick(View v){
            String inputAccountString = inputAccount.getText().toString();
            String inputPasswordString = inputPassword.getText().toString();
            memberManager.signup(inputAccountString,inputPasswordString);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
