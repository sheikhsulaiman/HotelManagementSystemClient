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

public class DashboardController implements Initializable {
    @FXML
    private Button btn_profile;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media bookings = new Media(Request.fetchBookings(Integer.toString(Log.userId)));
        String rawData = (bookings.getReceivedData());
        String[] list = rawData.split(":");
        for (String data:list) {
            System.out.println(Arrays.toString(data.split(",")));
            DButils.updateBookingTable(data.split(","));
        }

        btn_profile.setText(String.valueOf(Log.userId));

    }
}
