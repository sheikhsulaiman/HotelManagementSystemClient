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
import java.util.Arrays;
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

    @FXML
    private Button btn_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.changeScene(event,"../signup.fxml","Sign Up");
            }
        });

        btn_back.setOnAction(event -> SceneSwitcher.changeScene(event,"../app.fxml","big Mind Hotel"));

        btn_logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Media log = new Media( Request.logIn(tf_userId.getText(),tf_password.getText()));
                    String data = log.getReceivedData();
                    String[] list = data.split(",");
                //System.out.println(Arrays.toString(list));
                    if (list[0].equals(tf_userId.getText())) {
                        Log.setUserId(Integer.parseInt(list[0]));
                        Log.setFirstname(list[1]);
                        Log.setLastname(list[2]);
                        Log.setPhone(Integer.parseInt(list[3]));
                        Log.setGender(list[4]);
                        Log.setEmail(list[5]);
                        Log.setAddress(list[6]);
                        SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard");
                    } else {
                        l_errorMessage.setText("Wrong Credential");
                    }
                }
        });
    }
}
