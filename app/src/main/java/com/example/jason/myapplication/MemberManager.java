package com.example.jason.myapplication;

/**
 * Created by jason on 2017/5/30.
 */

public class MemberManager {
    private String account;
    private String password;
    private DataBase database;
    public MemberManager(DataBase database){
        this.database = database;
    }
    public boolean login(String account, String password){

        return database.queryMember(account,password);

    }

    public void signup(String account, String password){
        database.insertMember(account,password);
    }

    public void logout(){

    }
}
