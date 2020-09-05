package com.nklymok.mindspace.controller;

import com.jfoenix.controls.JFXComboBox;
import com.nklymok.mindspace.component.LanguageComboBox;
import com.nklymok.mindspace.component.ResourceBundles;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsStageController implements Initializable {
    @FXML
    private Button aboutButton;
    @FXML
    private LanguageComboBox languageComboBox;
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

    private final EventHandler<ActionEvent> comboBoxHandler = event -> {
        ResourceBundles.setLocale(languageComboBox.getLocale());
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageComboBox.setOnAction(comboBoxHandler);
        aboutButton.setOnAction(aboutButtonHandler);
        exitButton.setOnAction(exitButtonHandler);
    }
}
