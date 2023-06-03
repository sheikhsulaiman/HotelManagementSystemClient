package com.hotel.hotelclient.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection implements DataBase{
    private Connection databaseLink;
    public Connection getDatabaseLink(){
        String url = "jdbc:sqlite:src/main/java/com/hotel/hotelclient/database/hotel.db";
        try{
            databaseLink = DriverManager.getConnection(url);
        } catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
