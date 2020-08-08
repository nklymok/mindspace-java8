package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.component.PriorityComboBox;
import com.nklymok.mindspace.component.RepetitionComboBox;
import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.Subscriber;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TaskBuilderPaneController implements Initializable, Subscriber {
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
    @FXML
    private Button createTaskButton;

    private TaskService taskService;
    private TaskManagerController taskManagerController;
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
            TaskManagerController.getInstance().add(taskPaneController, recentItemPaneController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indicatorAction(ActionEvent actionEvent) {
        comboBoxIndicatorPane.setPriority(priorityComboBox.getPriority());
    }

    public void setSandboxPaneController(SandboxPaneController sandboxPaneController) {
        this.sandboxPaneController = sandboxPaneController;
    }

    public void setRecentsPaneController(RecentsPaneController recentsPaneController) {
        this.recentsPaneController = recentsPaneController;
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);
        // setting up the singletons
        taskService = TaskServiceImpl.getInstance();
        taskManagerController = TaskManagerController.getInstance();

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
}
