package com.example.sqlitedemo;

import android.widget.Button;

public class customer_model {


    String username,password;
   int id;



    @Override
    public String toString() {
        return " " +
        "id:" + id+
        "\nusername:" + username +
                "\npassword: " + password +"\n\n";
    }

    public customer_model(String username, String password, int id) {
        this.id=id;
        this.password=password;
        this.username=username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }
}
