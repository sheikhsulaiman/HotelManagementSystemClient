package com.hotel.hotelclient;

import com.hotel.hotelclient.communication.Client;
import com.hotel.hotelclient.database.DButils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                DButils.clearAllBookings();
                DButils.clearAllCalendar();
                DButils.clearAllRooms();
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {

        launch();
    }
}