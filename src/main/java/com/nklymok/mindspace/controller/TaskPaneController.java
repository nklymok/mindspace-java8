package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

public class TaskPaneController {

    private final TaskService taskService;

    private final TaskModel model;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private PriorityPaneController priorityPane;
    @FXML
    private Text headerText;
    @FXML
    private Text descriptionText;
    @FXML
    private Button editButton;
    @FXML
    private Text dueText;

    private double mouseX = 0.0d;
    private double mouseY = 0.0d;

    EventHandler<ActionEvent> editButtonHandler = event -> {
        System.out.println("Edit button clicked");
    };

    EventHandler<MouseEvent> mousePressHandler = e -> {
        rootPane.toFront();
        mouseX = e.getX();
        mouseY = e.getY();
    };

    EventHandler<MouseEvent> mouseDragHandler = e -> {
        double x = e.getX() + rootPane.getTranslateX() - mouseX,
                y = e.getY() + rootPane.getTranslateY() - mouseY;
        rootPane.setTranslateX(x);
        rootPane.setTranslateY(y);
        e.consume();
    };

    EventHandler<MouseEvent> mouseReleaseHandler = Event::consume;

    public TaskPaneController(TaskModel taskModel) {
        taskService = TaskServiceImpl.getInstance();
        model = taskModel;
    }

    @FXML
    public void initialize() {
        editButton.setOnAction(editButtonHandler);
        rootPane.setOnMouseDragged(mouseDragHandler);
        rootPane.setOnMousePressed(mousePressHandler);
        rootPane.setOnMouseReleased(mouseReleaseHandler);
        updateFields();
    }

    public void delete() {
        taskService.delete(model);
    }

    public void updateFields() {
        taskService.update(model);
        updateHeader();
        updateDescription();
        updateDueDate();
        updatePriority();
    }

    private void updateHeader() {
        headerText.setText(model.getHeader());
    }

    private void updateDescription() {
        descriptionText.setText(model.getDescription());
    }

    private void updateDueDate() {
        dueText.setText(model.getDueDate().format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")));
    }

    private void updatePriority() {
        priorityPane.setPriority(model.getPriority());
    }

    private boolean isLegal() {
        return false;
    }

    public TaskModel getModel() {
        return model;
    }
    //TODO isLegal - has legal boundarias and can leave them
}