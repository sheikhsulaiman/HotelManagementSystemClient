package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.utils.PdfExport;
import com.hotel.hotelclient.utils.SceneSwitcher;
import com.hotel.hotelclient.utils.Value;
import com.hotel.hotelclient.utils.pricechart.PriceChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.hotel.hotelclient.database.DButils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyBookingController implements Initializable {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_cancelBooking;

    @FXML
    private Button btn_predictPrice;

    @FXML
    private Button btn_printReceipt;

    @FXML
    private Button btn_updateBooking;

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
    private TextField tf_user_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> list = new ArrayList<>(9);

        list.addAll(DButils.getBookingDetails(Value.getIntegerValue()));
        tf_user_id.setText(list.get(1));
        dp_checkIn.setValue(LocalDate.parse(list.get(2)));
        dp_checkOut.setValue(LocalDate.parse(list.get(3)));
        cb_roomNo.setValue(list.get(0));
        cb_roomType.setValue(DButils.getRoomType(list.get(0)));
        cb_payType.setValue(list.get(4));
        cb_payStatus.setValue(list.get(5));
        if(list.get(6).equals("YES")) {
            ckb_roomService.setSelected(true);
        }else {
            ckb_roomService.setSelected(false);
        }
        if(list.get(7).equals("YES")) {
            ckb_poolAccess.setSelected(true);
        }else {
            ckb_poolAccess.setSelected(false);
        }
        if(list.get(8).equals("YES")) {
            ckb_carParking.setSelected(true);
        }else {
            ckb_carParking.setSelected(false);
        }
        l_predictedPrice.setText("$ "+Integer.toString(PriceChart.calculatePrice(cb_roomNo.getValue(),dp_checkIn.getValue(),dp_checkOut.getValue(), ckb_roomService.isSelected()?"YES":"NO", ckb_carParking.isSelected()?"YES":"NO", ckb_poolAccess.isSelected()?"YES":"NO")));

        btn_back.setOnAction(event -> SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard"));

        cb_payType.getItems().addAll("Cash","Online");
        cb_payStatus.getItems().addAll("Paid","Unpaid");

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

        btn_predictPrice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                l_predictedPrice.setText("$ "+Integer.toString(PriceChart.calculatePrice(cb_roomNo.getValue(),dp_checkIn.getValue(),dp_checkOut.getValue(), ckb_roomService.isSelected()?"YES":"NO", ckb_carParking.isSelected()?"YES":"NO", ckb_poolAccess.isSelected()?"YES":"NO")));
            }
        });

        btn_updateBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println(list);
                try {
                    Media updateBooking = new Media(Request.updateBooking(list.get(9),cb_roomNo.getValue(),list.get(1), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue()==null?"Cash":cb_payType.getValue(), cb_payStatus.getValue()==null?"Unpaid":cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO"));
                    //DButils.updateBooking(Integer.parseInt(list.get(9)),Integer.parseInt(cb_roomNo.getValue()), Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue(), cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO");
                    Media updateInvoice = new Media(Request.updateInvoice((list.get(9)),PriceChart.calculatePrice(cb_roomNo.getValue(), dp_checkIn.getValue(), dp_checkOut.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO"),cb_payStatus.getValue()));
                    Media updateMoneyVault = new Media(Request.updateMoneyVault((list.get(9)),cb_payStatus.getValue()));
                    SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard");
                    Alert alert =new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setContentText("Booking has been edited successfully");
                    alert.show();
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill up all the fields");
                    alert.show();
                }
            }
        });

        btn_cancelBooking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDialog.setTitle("Confirmation Dialog");
                confirmationDialog.setHeaderText("Are you sure you want to proceed?");
                confirmationDialog.setContentText("This action cannot be undone.");

                ButtonType buttonYes = new ButtonType("Yes");
                ButtonType buttonNo = new ButtonType("No");

                confirmationDialog.getButtonTypes().setAll(buttonYes, buttonNo);

                confirmationDialog.showAndWait().ifPresent(response -> {
                    if (response == buttonYes) {
                        Media deleteBooking = new Media(Request.deleteBooking((list.get(9))));
                        if(deleteBooking.getReceivedData().equals("true")){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            if(cb_payStatus.getValue().equals("Paid")) {
                                alert.setContentText("Your booking has been deleted.");
                            }else{
                                alert.setContentText("Your booking has been deleted.\nFor money refund contact to the reception.\nThank You.");
                            }
                            alert.show();
                        }
                        //DButils.deleteBooking(Integer.parseInt(list.get(9)));
                        SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard");
                    } else if (response == buttonNo) {
                    }
                });

            }
        });

        btn_printReceipt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //PdfExport.printInvoice(DButils.getInvoiceId(Integer.parseInt(list.get(9))),Integer.parseInt(list.get(9)),Integer.parseInt(cb_roomNo.getValue()), Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue(), cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO");

                //Media createInvoice = new Media(Request.createNewInvoice((list.get(0)), String.valueOf(PriceChart.calculatePrice(cb_roomNo.getValue(), dp_checkIn.getValue(), dp_checkOut.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO")),"Unpaid"));
                Media getInvoiceId = new Media(Request.fetchInvoiceId(list.get(0)));
                String invoiceId = getInvoiceId.getReceivedData();
                    PdfExport.printInvoice(Integer.parseInt(invoiceId),Integer.parseInt(list.get(0)) ,Integer.parseInt(cb_roomNo.getValue()), Integer.parseInt(tf_user_id.getText()), dp_checkIn.getValue().toString(), dp_checkOut.getValue().toString(), cb_payType.getValue()==null?"Cash":cb_payType.getValue(), cb_payStatus.getValue()==null?"Unpaid":cb_payStatus.getValue(), ckb_roomService.isSelected() ? "YES" : "NO", ckb_poolAccess.isSelected() ? "YES" : "NO", ckb_carParking.isSelected() ? "YES" : "NO");
            }
        });
    }
}
