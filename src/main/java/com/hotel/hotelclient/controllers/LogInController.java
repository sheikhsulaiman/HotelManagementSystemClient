package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.utils.Log;
import com.hotel.hotelclient.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    @FXML
    private Label l_errorMessage;

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
                Media log = new Media( Request.logIn(tf_userId.getText(),tf_password.getText()));

                    if (log.getReceivedData().equals("true")) {
                        Log.userId=Integer.parseInt(tf_userId.getText());
                        SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard");
                    } else {
                        l_errorMessage.setText("Wrong Credential");
                    }
                }
        });
    }
}
