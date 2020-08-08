package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.component.PriorityComboBox;
import com.nklymok.mindspace.component.Repeats;
import com.nklymok.mindspace.component.RepetitionComboBox;
import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.Subscriber;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class EditStageController implements Initializable, Subscriber {
    @FXML
    private Button exitButton;
    @FXML
    private Button saveAndExitButton;
    @FXML
    private TextField fieldHeader;
    @FXML
    private TextArea fieldDescription;
    @FXML
    private PriorityComboBox priorityComboBox;
    @FXML
    private RepetitionComboBox repetitionComboBox;
    @FXML
    private PriorityPaneController comboBoxIndicatorPane;
    @FXML
    private DateTimePicker dateTimePicker;

    private TaskService taskService;
    private TaskModel model;

    EventHandler<ActionEvent> exitButtonHandler = event -> {
        // close edit window
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    };

    EventHandler<ActionEvent> saveAndExitButtonHandler = event -> {
        model.setHeader(fieldHeader.getText());
        model.setDescription(fieldDescription.getText());
        model.setDueDate(dateTimePicker.getDateTimeValue());
        model.setPriority(priorityComboBox.getPriority());
        taskService.update(model);
        TaskManagerController.getInstance().modelToController(model).updateFields();

        // close edit window
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    };

    public EditStageController(TaskModel model) {
        this.model = model;
    }

    public void indicatorAction(ActionEvent actionEvent) {
        comboBoxIndicatorPane.setPriority(priorityComboBox.getPriority());
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // getting the singletons
        AppEventBus.register(this);
        taskService = TaskServiceImpl.getInstance();

        // setting existing values
        fieldHeader.setText(model.getHeader());
        fieldDescription.setText(model.getDescription());
        priorityComboBox.setPriority(model.getPriority());
        repetitionComboBox.setValue(Repeats.None); //TODO: add repeats
        dateTimePicker.setDateTimeValue(model.getDueDate());

        // setting listeners
        exitButton.setOnAction(exitButtonHandler);
        saveAndExitButton.setOnAction(saveAndExitButtonHandler);
        priorityComboBox.setOnAction(this::indicatorAction);
    }
}
