package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.database.DButils;
import com.hotel.hotelclient.utils.Log;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController implements Initializable {
    @FXML
    private Button btn_profile;
    @FXML
    private Label l_welcome;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media bookings = new Media(Request.fetchBookings(Integer.toString(Log.userId)));
        String rawBookingData = (bookings.getReceivedData());
        String[] listBooking = rawBookingData.split(":");
        //System.out.println(Arrays.toString(listBooking));
        for (String data:listBooking) {
            //System.out.println(Arrays.toString(data.split(",")));
            DButils.updateBookingTable(data.split(","));
        }
        Media rooms = new Media(Request.fetchRooms());
        String rawRoomsData = rooms.getReceivedData();
        String[] listRoom = rawRoomsData.split(":");
        //System.out.println(Arrays.toString(listRoom));
        for (String data:listRoom) {
            //System.out.println(Arrays.toString(data.split(",")));
            DButils.updateRoomsTable(data.split(","));
        }

        l_welcome.setText("Welcome "+Log.getFirstname()+" "+Log.getLastname());

        btn_profile.setText(String.valueOf(Log.userId));

    }
}
