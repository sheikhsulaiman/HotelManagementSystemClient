package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private Button btn_about;

    @FXML
    private Button btn_bookDouble;

    @FXML
    private Button btn_bookSingle;

    @FXML
    private Button btn_contact;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_logIn;

    @FXML
    private Button btn_signup;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.changeScene(event,"../login.fxml","Log In");
            }
        });

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneSwitcher.changeScene(event,"../signup.fxml","Sign Up");
            }
        });
    }
}
