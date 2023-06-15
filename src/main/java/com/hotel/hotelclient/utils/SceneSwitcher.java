package com.hotel.hotelclient.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitcher {

    public static void changeScene(ActionEvent event,String fxmlFile,String title){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
        try{
            root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(SceneSwitcher.class.getResource("../styles.css")).toExternalForm());

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void changeScene(ContextMenuEvent contextMenuEvent, String fxmlFile, String title) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlFile));
        try{
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)contextMenuEvent.getSource()).getScene().getWindow();
            scene.getStylesheets().add(Objects.requireNonNull(SceneSwitcher.class.getResource("../styles.css")).toExternalForm());

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
