package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskBuilderPaneController {
    @FXML
    private TextField fieldHeader;
    @FXML
    private TextArea fieldDescription;
    @FXML
    private ComboBox priorityComboBox;
    @FXML
    private Button createTaskButton;
    @FXML
    private PriorityPaneController comboBoxIndicatorPane;
    @FXML
    private ComboBox repetitionComboBox;
    @FXML
    private DateTimePicker dateTimePicker;

    private TaskService taskService;
    private SandboxPaneController sandboxPaneController;
    private RecentsPaneController recentsPaneController;

    EventHandler<ActionEvent> createTaskButtonHandler = event -> {
        TaskModel taskModel = TaskModel
                .builder()
                .header(fieldHeader.getText())
                .description(fieldDescription.getText())
                .dueDate(dateTimePicker.getDateTimeValue())
                .priority(comboBoxIndicatorPane.getIntValue())
                .build();
        taskModel = taskService.save(taskModel);
        createTask(taskModel);
    };

    public void createTask(TaskModel taskModel) {
        FXMLLoader taskFxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/task-pane.fxml"));
        TaskPaneController taskPaneController = new TaskPaneController(taskModel);
        taskFxmlLoader.setController(taskPaneController);

        FXMLLoader recentFxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/recent-item-pane.fxml"));
        RecentItemPaneController recentItemPaneController = new RecentItemPaneController(taskModel);
        recentFxmlLoader.setController(recentItemPaneController);

        try {
            sandboxPaneController.addTask(taskFxmlLoader.load(), taskModel);
            recentsPaneController.addRecent(recentFxmlLoader.load(), taskModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        //Setting up the singletons
        taskService = TaskServiceImpl.getInstance();

        // setting button listener
            createTaskButton.setOnAction(createTaskButtonHandler);

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

    public void setSandboxPaneController(SandboxPaneController sandboxPaneController) {
        this.sandboxPaneController = sandboxPaneController;
    }

    public void setRecentsPaneController(RecentsPaneController recentsPaneController) {
        this.recentsPaneController = recentsPaneController;
    }
}
