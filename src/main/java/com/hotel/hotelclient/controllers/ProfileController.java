package com.hotel.hotelclient.controllers;


import com.hotel.hotelclient.communication.Media;
import com.hotel.hotelclient.communication.Request;
import com.hotel.hotelclient.database.DButils;
import com.hotel.hotelclient.utils.Log;
import com.hotel.hotelclient.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

        @FXML
        private Button btn_back;

        @FXML
        private Button btn_logout;

        @FXML
        private Button btn_update;

        @FXML
        private ChoiceBox<String> cb_gender;

        @FXML
        private Label l_errorMessage;

        @FXML
        private PasswordField pf_password_old;

        @FXML
        private PasswordField pf_password_new;

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

        private String[] gender={"male","female"};
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                btn_back.setOnAction(event -> SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard"));
                tf_firstname.setText(Log.getFirstname());
                tf_lastname.setText(Log.getLastname());
                tf_phone.setText(String.valueOf(Log.getPhone()));
                tf_email.setText(Log.getEmail());
                cb_gender.setValue(Log.getGender());
                ta_address.setText(Log.getAddress());

                cb_gender.getItems().clear();
                cb_gender.getItems().addAll(gender);

                btn_logout.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                                DButils.clearAll();
                                SceneSwitcher.changeScene(event,"../app.fxml","Hotel");
                        }
                });

                btn_update.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                                if(pf_password_old.getText().isEmpty()){
                                        l_errorMessage.setText("Your current password is needed");
                                }else{
                                        Media checkPass = new Media(Request.logIn(String.valueOf(Log.getUserId()), pf_password_old.getText()));
                                        if (checkPass.getReceivedData().equals("false")) {
                                                l_errorMessage.setText("Wrong Password");
                                        } else{
                                                Media updateProfile = new Media(Request.updateProfile(tf_firstname.getText(), tf_lastname.getText(), tf_phone.getText(), cb_gender.getValue(), tf_email.getText(), ta_address.getText(), String.valueOf(Log.getUserId()), pf_password_new.getText()));
                                                if (updateProfile.getReceivedData().equals("true")) {

                                                        Media log = new Media( Request.logIn(String.valueOf(Log.getUserId()),pf_password_new.getText().isEmpty()?pf_password_old.getText():pf_password_new.getText()));
                                                        String rawLogData = log.getReceivedData();
                                                        String[] list = rawLogData.split("~");
                                                        //System.out.println(Arrays.toString(list));
                                                        if (list[0].equals(String.valueOf(Log.getUserId()))) {
                                                                Log.setUserId(Integer.parseInt(list[0]));
                                                                Log.setFirstname(list[1]);
                                                                Log.setLastname(list[2]);
                                                                Log.setPhone(Integer.parseInt(list[3]));
                                                                Log.setGender(list[4]);
                                                                Log.setEmail(list[5]);
                                                                Log.setAddress(list[6]);
                                                                SceneSwitcher.changeScene(event,"../dashboard.fxml","Dashboard");
                                                        }
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                                        alert.setContentText("Successfully Updated.");
                                                        alert.show();
                                                } else {
                                                        l_errorMessage.setText("Something went wrong, Please try again.");
                                                }
                                        }
                                }

                        }
                });

        }
}
