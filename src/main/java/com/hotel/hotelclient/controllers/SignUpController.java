package com.hotel.hotelclient.controllers;

//import com.hotel.hoteladmin.DButils.DButils;
import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.communication.Request;
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

public class SignUpController implements Initializable {

    @FXML
    private Button btn_Resigter;

    @FXML
    private ChoiceBox<String> cb_gender;

    @FXML
    private TextArea ta_address;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_firstname;

    @FXML
    private TextField tf_lastname;

    @FXML
    private TextField tf_phone;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Button btn_back;

    private String[] gender={"male","female"};

    //public static int generatedId;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cb_gender.getItems().addAll(gender);

        btn_back.setOnAction(event -> SceneSwitcher.changeScene(event,"../app.fxml","big Mind Hotel"));

        btn_Resigter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Media registration = new Media(Request.newUserRegistration(tf_firstname.getText(),tf_lastname.getText(),pf_password.getText(),tf_phone.getText(),cb_gender.getValue(),tf_email.getText(),ta_address.getText()));
                String data = registration.getReceivedData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successfully Registered");
                alert.setContentText("Your User Id is : "+data);
                alert.show();
                SceneSwitcher.changeScene(event,"../login.fxml","Log in");
            }
        });

    }
}
