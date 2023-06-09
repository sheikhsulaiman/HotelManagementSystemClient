package com.hotel.hotelclient.controllers;

import com.hotel.hotelclient.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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
    private Button btn_bookMulti;

    @FXML
    private Button btn_contact;

    @FXML
    private AnchorPane back_image;


    @FXML
    private Button btn_logIn;

    @FXML
    private Button btn_signup;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        back_image.getStyleClass().clear();
        back_image.getStyleClass().add("background-pane");

        btn_contact.setOnAction(event -> SceneSwitcher.changeScene(event,"../contact.fxml","Contact"));
        btn_about.setOnAction(event -> SceneSwitcher.changeScene(event,"../about.fxml","About"));

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

        btn_bookSingle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please Log in for new booking.");
                alert.show();
                SceneSwitcher.changeScene(event,"../login.fxml","Log in");
            }
        });
        btn_bookDouble.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please Log in for new booking.");
                alert.show();
                SceneSwitcher.changeScene(event,"../login.fxml","Log in");

            }
        });
        btn_bookMulti.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please Log in for new booking.");
                alert.show();
                SceneSwitcher.changeScene(event,"../login.fxml","Log in");

            }
        });
    }
}
