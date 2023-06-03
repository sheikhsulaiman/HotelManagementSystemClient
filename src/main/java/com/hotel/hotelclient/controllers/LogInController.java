package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button btn_logIn;

    @FXML
    private Button btn_signUp;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.changeScene(event,"../signup.fxml","Sign Up");
            }
        });

        btn_logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Media sender = new Media( Request.logIn(tf_userId.getText(),tf_password.getText()));

                    if (Media.getReceivedData().equals("true")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Success " + Media.getReceivedData());
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Error " + Media.getReceivedData());
                        alert.show();
                    }
                }
        });
    }
}
