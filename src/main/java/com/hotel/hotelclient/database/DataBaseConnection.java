package com.hotel.hotelclient.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection implements DataBase{
    private Connection databaseLink=null;
    public Connection getDatabaseLink() {
        String url = "jdbc:sqlite:src/main/java/com/hotel/hotelclient/database/hotel.db";
        if (databaseLink == null){
            try {
                databaseLink =(Connection) DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                databaseLink.close();
                databaseLink =(Connection) DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return databaseLink;
    }
}
