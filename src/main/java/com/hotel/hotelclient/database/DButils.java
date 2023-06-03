package com.hotel.hotelclient.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DButils {
    public static void updateBookingTable(String[] list){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try {
            PreparedStatement updateBookingStatement = connectDB.prepareStatement("insert into bookings(roomno, userid, checkin, checkout, paymentmethod, paymentstatus, roomservice, poolaccess, carparking,bookingid) values (?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement updateCalenderStatement = connectDB.prepareStatement("insert into calendar(roomid,bookingid,start,end) values (?,?,?,?)");

            updateBookingStatement.setInt(1,Integer.parseInt( list[1]));
            updateCalenderStatement.setInt(1,Integer.parseInt( list[1]));
            updateBookingStatement.setInt(2,Integer.parseInt( list[2]));
            updateCalenderStatement.setInt(2,Integer.parseInt( list[0]));

            updateBookingStatement.setString(3, list[3]);
            updateCalenderStatement.setString(3, list[3]);
            updateBookingStatement.setString(4,  list[4]);
            updateCalenderStatement.setString(4,  list[4]);
            updateBookingStatement.setString(5,  list[5]);
            updateBookingStatement.setString(6,  list[6]);
            updateBookingStatement.setString(7,  list[7]);
            updateBookingStatement.setString(8,  list[8]);
            updateBookingStatement.setString(9,  list[9]);
            updateBookingStatement.setInt(10,Integer.parseInt( list[0]));

            updateBookingStatement.executeUpdate();
            updateCalenderStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }
    }
    public static void clearAll(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        try {
            Statement clearBookingStatement = connectDB.createStatement();
            Statement clearCalendarStatement = connectDB.createStatement();
            Statement clearRoomsStatement = connectDB.createStatement();

            clearBookingStatement.execute("DELETE FROM bookings WHERE 1");
            clearCalendarStatement.execute("DELETE FROM calendar WHERE 1");
            clearRoomsStatement.execute("DELETE FROM rooms WHERE 1");
            connectDB.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
