package com.nklymok.mindspace.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsStageController implements Initializable {
    @FXML
    private Button aboutButton;
    @FXML
    private JFXComboBox<String> languageComboBox;
    @FXML
    private Button exitButton;

    private EventHandler<ActionEvent> aboutButtonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/knazariy/mindspace-java8").toURI());
                exitButton.fire();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    };

    private final EventHandler<ActionEvent> exitButtonHandler = event -> {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutButton.setOnAction(aboutButtonHandler);
        exitButton.setOnAction(exitButtonHandler);
    }
}
