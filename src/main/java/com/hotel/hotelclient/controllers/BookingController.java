package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.utils.Log;
import com.hotel.hotelclient.utils.PdfExport;
import com.hotel.hotelclient.utils.SceneSwitcher;
import com.hotel.hotelclient.database.DButils;
import com.hotel.hotelclient.utils.Value;
import com.hotel.hotelclient.utils.pricechart.PriceChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_confirmBooking;

    @FXML
    private Button btn_predictPrice;

    @FXML
    private ChoiceBox<String> cb_payStatus;

    @FXML
    private ChoiceBox<String> cb_payType;

    @FXML
    private ChoiceBox<String> cb_roomNo;

    @FXML
    private ChoiceBox<String> cb_roomType;

    @FXML
    private CheckBox ckb_carParking;

    @FXML
    private CheckBox ckb_poolAccess;

    @FXML
    private CheckBox ckb_roomService;

    @FXML
    private DatePicker dp_checkIn;

    @FXML
    private DatePicker dp_checkOut;

    @FXML
    private Label l_predictedPrice;

    @FXML
    private RadioButton rBtn_print;

    @FXML
    private TextField tf_user_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_back.setOnAction(event -> SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard"));

        cb_roomType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    cb_roomNo.getItems().clear();
                    ArrayList<String> availableRooms = new ArrayList<>(9);
                    availableRooms = (DButils.getRooms(cb_roomType.getValue()));
                    availableRooms.removeAll(DButils.getBookedRooms(dp_checkIn, dp_checkOut));

                    cb_roomNo.getItems().addAll(availableRooms);
                }catch (NullPointerException e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Please pick a date");
                    alert.show();
                }
            }
        });

        cb_payType.getItems().addAll("Cash","Online");
        tf_user_id.setText(Integer.toString(Log.getUserId()));
        cb_payStatus.setValue("Unpaid");

        dp_checkIn.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                LocalDate today = LocalDate.now();
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        dp_checkOut.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today;
                if(dp_checkIn.getValue()!=null)
                    today = dp_checkIn.getValue();
                else
                    today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        cb_roomType.getItems().add("any");
        cb_roomType.getItems().addAll(DButils.getRoomType());

        btn_confirmBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Media newBooking = new Media(Request.newBooking(cb_roomNo.getValue(), tf_user_id.getText(), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue() == null ? "Cash" : cb_payType.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO"));
                    Media bookings = new Media(Request.getBookingDetailsForClient());
                    System.out.println(bookings.getReceivedData());
                    String rawBookingData = (bookings.getReceivedData());
                    if(!(rawBookingData.isBlank()||rawBookingData.isEmpty())) {
                        String[] listBooking = rawBookingData.split(":");
                        //System.out.println(Arrays.toString(listBooking));
                        for (String data : listBooking) {
                            //System.out.println(Arrays.toString(data.split(",")));
                            DButils.clearAll();
                                DButils.updateBookingTable(data.split("~"));
                        }
                    }
                    int bookingId = DButils.getLastBookingId();
                    Log.setRoomNumber(Integer.parseInt(cb_roomNo.getValue()));
                    Log.setRoomType(DButils.getRoomType(Integer.toString(Log.getRoomNumber())));
                    System.out.println(DButils.getRoomType(Integer.toString(Log.getRoomNumber())));
                    //String roomNo = (cb_roomNo.getValue());
                    Media createInvoice = new Media(Request.createNewInvoice(Integer.toString(bookingId), String.valueOf(PriceChart.calculatePrice(Log.getRoomType(), dp_checkIn.getValue(), dp_checkOut.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO")),"Unpaid"));
                    Media getInvoiceId = new Media(Request.fetchInvoiceId(String.valueOf(bookingId)));
                    String invoiceId = getInvoiceId.getReceivedData();
                    if(rBtn_print.isSelected()){
                        PdfExport.printInvoice(Integer.parseInt(invoiceId),bookingId,Integer.parseInt(cb_roomNo.getValue()), Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue()==null?"Cash":cb_payType.getValue(), cb_payStatus.getValue()==null?"Unpaid":cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO");
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill up all the fields");
                    alert.show();
                }
                SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard");
            }
        });

        btn_predictPrice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    l_predictedPrice.setText("$ " + Integer.toString(PriceChart.calculatePrice(Log.getRoomType(), dp_checkIn.getValue(), dp_checkOut.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO")));
                }catch (NumberFormatException e){}
            }
        });

    }
}