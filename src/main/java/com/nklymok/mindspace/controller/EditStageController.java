package com.nklymok.mindspace.controller;

import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.component.PriorityComboBox;
import com.nklymok.mindspace.component.Repeats;
import com.nklymok.mindspace.component.RepetitionComboBox;
import com.nklymok.mindspace.component.ResourceBundles;
import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.Subscriber;
import com.nklymok.mindspace.eventsystem.TaskEditEvent;
import com.nklymok.mindspace.eventsystem.TaskUpdateEvent;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tornadofx.control.DateTimePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

//TODO
// high-dpi bat file script (check pinned) - SOLVED
// fill archive -
// localization - DONE
// repeats -
// readme - DONE
// background images for sandbox pane - DONE
// release - DONE

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

    private final EventHandler<ActionEvent> exitButtonHandler = event -> {
        // close edit window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    };

    private final EventHandler<ActionEvent> saveAndExitButtonHandler = event -> {
        updateModelFields();
        taskService.update(model);
        AppEventBus.post(new TaskUpdateEvent(model));

        // close edit window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    };

    public EditStageController(TaskModel model) {
        this.model = model;
    }

    @FXML
    private void priorityComboBoxAction(ActionEvent actionEvent) {
        comboBoxIndicatorPane.setPriority(priorityComboBox.getPriority());
    }

    private void updateModelFields() {
        model.setHeader(fieldHeader.getText());
        model.setDescription(fieldDescription.getText());
        model.setDueDate(dateTimePicker.getDateTimeValue());
        model.setPriority(priorityComboBox.getPriority());
    }

    public void setFields() {
        fieldHeader.setText(model.getHeader());
        fieldDescription.setText(model.getDescription());
        priorityComboBox.setPriority(model.getPriority());
        comboBoxIndicatorPane.setPriority(model.getPriority());
        repetitionComboBox.setValue(Repeats.None);
        dateTimePicker.setDateTimeValue(model.getDueDate());
    }

    public void setActionListeners() {
        exitButton.setOnAction(exitButtonHandler);
        saveAndExitButton.setOnAction(saveAndExitButtonHandler);
        priorityComboBox.setOnAction(this::priorityComboBoxAction);
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);

        // getting the singletons
        taskService = TaskServiceImpl.getInstance();

        // setting existing values
        setFields();

        // setting listeners
        setActionListeners();

        // setting passed calendar day cells to disabled & custom styles
        dateTimePicker.setDayCellFactory(datePicker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date.compareTo(LocalDate.now()) < 0) {
                    setDisable(true);
                    setStyle("-fx-background-color: #F4B4B4;");
                }
            }
        });
    }
}
