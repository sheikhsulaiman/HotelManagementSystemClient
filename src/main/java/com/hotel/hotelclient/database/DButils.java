package com.hotel.hotelclient.database;

import com.hotel.hotelclient.utils.tables.Bookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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

    public static void updateRoomsTable(String[] list ){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try {
            PreparedStatement updateRoomsStatement = connectDB.prepareStatement("INSERT INTO rooms(number,type) VALUES (?,?)");

            updateRoomsStatement.setInt(1,Integer.parseInt(list[0]));
            updateRoomsStatement.setString(2,list[1]);

            updateRoomsStatement.executeUpdate();
            connectDB.close();

        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }
    }

    public static String getRoomType(String roomNo){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        String roomType="";

        try{
            PreparedStatement getRoomTypeStm = connectDB.prepareStatement( "SELECT type FROM rooms WHERE number=?");
            getRoomTypeStm.setInt(1,Integer.parseInt(roomNo));
            ResultSet resultSet = getRoomTypeStm.executeQuery();
            while (resultSet.next()){
                roomType = (resultSet.getString("type"));
            }
            connectDB.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception");
        }
        return roomType;
    }

    public static ObservableList<Bookings> getBookingTable() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getDatabaseLink();

        String userDetailsViewQuery = "SELECT * FROM bookings";
        ObservableList<Bookings> searchModelObservableList = FXCollections.observableArrayList();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(userDetailsViewQuery);
            while (queryOutput.next()) {
                Integer queryBookingId = queryOutput.getInt("bookingid");
                Integer queryRoomNo = queryOutput.getInt("roomno");
                //Integer queryUserid = queryOutput.getInt("userid");
                String queryCheckIn = queryOutput.getString("checkin");
                String queryCheckOut = queryOutput.getString("checkout");
                String queryPayMethod = queryOutput.getString("paymentmethod");
                String queryPayStatus = queryOutput.getString("paymentstatus");
                String queryRoomService = queryOutput.getString("roomservice");
                String queryPoolAccess = queryOutput.getString("poolaccess");
                String queryCarParking = queryOutput.getString("carparking");
                String roomType = DButils.getRoomType(Integer.toString(queryRoomNo));

                searchModelObservableList.add(new Bookings(queryBookingId,queryRoomNo,roomType,queryCheckIn,queryCheckOut,queryPayMethod,queryPayStatus,queryRoomService,queryCarParking,queryPoolAccess));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return searchModelObservableList;

    }
}
