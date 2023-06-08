package com.hotel.hotelclient.database;

import com.hotel.hotelclient.utils.tables.Bookings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import com.hotel.hotelclient.utils.daterangechecker.DateRangeComparator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DButils {
    public static void updateBookingTable(String[] list){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        try {
            PreparedStatement insertBookingStatement = connectDB.prepareStatement("insert into bookings(roomno, userid, checkin, checkout, paymentmethod, paymentstatus, roomservice, poolaccess, carparking,bookingid) values (?,?,?,?,?,?,?,?,?,?)");
            insertBookingStatement.setInt(1, Integer.parseInt(list[1]));
            insertBookingStatement.setInt(2, Integer.parseInt(list[2]));
            insertBookingStatement.setString(3, list[3]);
            insertBookingStatement.setString(4, list[4]);
            insertBookingStatement.setString(5, list[5]);
            insertBookingStatement.setString(6, list[6]);
            insertBookingStatement.setString(7, list[7]);
            insertBookingStatement.setString(8, list[8]);
            insertBookingStatement.setString(9, list[9]);
            insertBookingStatement.setInt(10, Integer.parseInt(list[0]));
            insertBookingStatement.executeUpdate();
            connectDB.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private static void setStatements(String[] list, PreparedStatement BookingStatement) throws SQLException {
        BookingStatement.setInt(2, Integer.parseInt(list[2]));
        BookingStatement.setString(3, list[3]);
        BookingStatement.setString(4, list[4]);
        BookingStatement.setString(5, list[5]);
        BookingStatement.setString(6, list[6]);
        BookingStatement.setString(7, list[7]);
        BookingStatement.setString(8, list[8]);
        BookingStatement.setString(9, list[9]);
        BookingStatement.setInt(10, Integer.parseInt(list[0]));
        BookingStatement.executeUpdate();
    }

    public static void updateCalendarTable(String[] list){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();


        try {
            PreparedStatement insertCalenderStatement = connectDB.prepareStatement("insert into calendar(id,roomid,start,end) values (?,?,?,?)");
            insertCalenderStatement.setInt(1, Integer.parseInt(list[0]));
            insertCalenderStatement.setInt(2, Integer.parseInt(list[1]));
            insertCalenderStatement.setString(3, list[2]);
            insertCalenderStatement.setString(4, list[3]);
            insertCalenderStatement.executeUpdate();
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception calendar");
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
            e.printStackTrace();
        }
    }

    public static void updateRoomsTable(String[] list ) {
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        PreparedStatement updateRoomsStatement = null;
        try {
            updateRoomsStatement = connectDB.prepareStatement("INSERT INTO rooms(number,type) VALUES (?,?)");
            updateRoomsStatement.setString(2, list[1]);
            updateRoomsStatement.setInt(1, Integer.parseInt(list[0]));

            updateRoomsStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static ArrayList<String> getRooms(String roomType){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        ArrayList<String> list = new ArrayList<>(9);

//        String connectQuery = "SELECT DISTINCT type FROM rooms";
        try {
            PreparedStatement getAllRooms = connectDB.prepareStatement("select number from rooms");
            PreparedStatement getRoomsStm = connectDB.prepareStatement("select number from rooms where type=?");
            getRoomsStm.setString(1,roomType);
            ResultSet resultSet;
            if(roomType.equals("any")){resultSet= getAllRooms.executeQuery();}
            else {resultSet = getRoomsStm.executeQuery(); }
            while (resultSet.next()){
                list.add(Integer.toString(resultSet.getInt("number")));
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception");
        }
        return list;
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
            connectDB.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchModelObservableList;

    }

    public static ArrayList<String> getBookedRooms(DatePicker dp_checkin, DatePicker dp_checkout){
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connectDB = dataBaseConnection.getDatabaseLink();
        ArrayList<String> list = new ArrayList<>(9);

        try {
            PreparedStatement getDates = connectDB.prepareStatement("SELECT * FROM calendar");
            ResultSet resultSet = getDates.executeQuery();

            String db_start,db_end;
            int roomid;
            while (resultSet.next()){
                db_start=(resultSet.getString("start"));
                db_end=(resultSet.getString("end"));
                roomid=resultSet.getInt("roomid");
                if(DateRangeComparator.compare(dp_checkin,dp_checkout,db_start,db_end)){
                    list.add(Integer.toString(roomid));
                }
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> getRoomType(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        String connectQuery = "SELECT DISTINCT type FROM rooms";
        ArrayList<String> list = new ArrayList<>(3);

        try{
            Statement statement = connectDB.createStatement();
            ResultSet resultSet = statement.executeQuery(connectQuery);
            while (resultSet.next()){
                list.add(resultSet.getString("type"));
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception");
        }
        return list;
    }

    public static int getLastBookingId(){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();
        int id=0;

        try {
            Statement getLastIdStatement = connectDB.createStatement();
            ResultSet resultSet = getLastIdStatement.executeQuery("SELECT bookingid FROM bookings WHERE ROWID IN ( SELECT max( ROWID ) FROM bookings)" );

            id=resultSet.getInt("bookingid");
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
//            System.out.println("SQL Exception");
        }
        return id;
    }

    public static ArrayList<String> getBookingDetails(Integer bookingid){
        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connectDB = dbConnection.getDatabaseLink();

        ArrayList<String> list = new ArrayList<>(10);

        try{
            PreparedStatement getStm =connectDB.prepareStatement( "SELECT * FROM bookings WHERE bookingid=?");
            getStm.setInt(1,bookingid);
            ResultSet resultSet = getStm.executeQuery();
            while (resultSet.next()){
                list.add(Integer.toString(resultSet.getInt(2)));
                list.add(Integer.toString(resultSet.getInt(3)));
                list.add((resultSet.getString(4)));
                list.add((resultSet.getString(5)));
                list.add((resultSet.getString(6)));
                list.add((resultSet.getString(7)));
                list.add((resultSet.getString(8)));
                list.add((resultSet.getString(9)));
                list.add((resultSet.getString(10)));
                list.add((Integer.toString(resultSet.getInt("bookingid"))));
            }
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("SQL Exception");
        }
        return list;
    }


}
