package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.database.DButils;
import com.hotel.hotelclient.utils.Log;
import com.hotel.hotelclient.utils.SceneSwitcher;
import com.hotel.hotelclient.utils.Search;
import com.hotel.hotelclient.utils.tables.Bookings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController implements Initializable {
    @FXML
    private Button btn_profile;
    @FXML
    private Button btn_reload;

    @FXML
    private Label l_welcome;

    @FXML
    private Button btn_newbooking;

    @FXML
    private TextField tf_search;

    @FXML
    private TableColumn<Bookings,Integer> tc_bookingId;

    @FXML
    private TableColumn<Bookings,Integer> tc_btRoomNumber;

    @FXML
    private TableColumn<Bookings,String> tc_btRoomType;

    @FXML
    private TableColumn<Bookings,String> tc_carParking;

    @FXML
    private TableColumn<Bookings,String> tc_checkIn;

    @FXML
    private TableColumn<Bookings,String> tc_checkOut;

    @FXML
    private TableColumn<Bookings,String> tc_payStatus;

    @FXML
    private TableColumn<Bookings,String> tc_payType;

    @FXML
    private TableColumn<Bookings,String> tc_poolAccess;

    @FXML
    private TableColumn<Bookings,String> tc_roomService;

    @FXML
    private TableView<Bookings> tv_bookings;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        btn_newbooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.changeScene(event,"../booking.fxml","New Booking");
            }
        });

        l_welcome.setText("Welcome "+Log.getFirstname()+" "+Log.getLastname());

        btn_profile.setText(String.valueOf(Log.userId));


        btn_reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {



                //Booking Table
                ObservableList<Bookings> searchModelBookingObservableList = DButils.getBookingTable();
                tc_bookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
                tc_btRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
                tc_btRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
                tc_checkIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
                tc_checkOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
                tc_payType.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
                tc_payStatus.setCellValueFactory(new PropertyValueFactory<>("payStatus"));
                tc_roomService.setCellValueFactory(new PropertyValueFactory<>("roomService"));
                tc_carParking.setCellValueFactory(new PropertyValueFactory<>("carParking"));
                tc_poolAccess.setCellValueFactory(new PropertyValueFactory<>("poolAccess"));

                tv_bookings.setItems(searchModelBookingObservableList);
                Search.bookingSearch(tf_search,searchModelBookingObservableList,tv_bookings);
            }
        });



    }
}
