package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.component.ResourceBundles;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class ToolsPaneController implements Initializable {
    @FXML
    private Button settingsButton;
    @FXML
    private Button archiveButton;

    private final EventHandler<ActionEvent> settingsButtonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            openSettings();
        }
    };

    private final EventHandler<ActionEvent> archiveButtonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            openArchive();
        }
    };

    private void openArchive() {
        ResourceBundle resourceBundle = ResourceBundles.getResourceBundle();
        FXMLLoader editStageFXMLLoader = new FXMLLoader(
                getClass().getResource("/fxmls/archive-stage.fxml"),
                resourceBundle);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        try {
            Scene archiveScene = new Scene(editStageFXMLLoader.load());
            archiveScene.setFill(Color.TRANSPARENT);
            stage.setScene(archiveScene);
            stage.setOnShown(event -> BlurEffect.getInstance().blur());
            stage.setOnHidden(event -> BlurEffect.getInstance().unblur());
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        ResourceBundle resourceBundle = ResourceBundles.getResourceBundle();
        FXMLLoader editStageFXMLLoader = new FXMLLoader(
                getClass().getResource("/fxmls/settings-stage.fxml"),
                resourceBundle);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        try {
            Scene settingsScene = new Scene(editStageFXMLLoader.load());
            settingsScene.setFill(Color.TRANSPARENT);
            stage.setScene(settingsScene);
            stage.setOnShown(event -> BlurEffect.getInstance().blur());
            stage.setOnHidden(event -> BlurEffect.getInstance().unblur());
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsButton.setOnAction(settingsButtonHandler);
        archiveButton.setOnAction(archiveButtonHandler);
    }
}
