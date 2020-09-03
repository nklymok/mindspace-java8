package com.nklymok.mindspace.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ArchiveStageController implements Initializable {

    @FXML
    private Hyperlink emptyAllHL;
    @FXML
    private Button exitButton;

    EventHandler<ActionEvent> htEmptyAllHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("Emptying the list");
        }
    };

    private final EventHandler<ActionEvent> exitButtonHandler = event -> {
        // close edit window
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emptyAllHL.setOnAction(htEmptyAllHandler);
        exitButton.setOnAction(exitButtonHandler);
    }
}
