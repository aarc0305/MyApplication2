package com.example.jason.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jason on 2017/5/30.
 */

public class DataBase {
    private SQLiteDatabase sqliteDB;
    public DataBase(SQLiteDatabase sqliteDB){
        this.sqliteDB = sqliteDB;
    }
    public boolean queryMember (String account,String password){
        System.out.println("1");
        Cursor query = sqliteDB.query("member_table", new String[] {"account", "password"}, null, null, null, null, null, null);
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
        sqliteDB.execSQL("DROP TABLE member_table");
        String createTable01 = "CREATE TABLE member_table(_id INTEGER PRIMARY KEY , account TEXT,password TEXT)";
        sqliteDB.execSQL(createTable01);
        String insert_1 = "INSERT INTO member_table (account,password) values ('"+account+"','"+password+"')";
        sqliteDB.execSQL(insert_1);
    }
}
