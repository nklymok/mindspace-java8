package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.model.TaskModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

public class RecentItemPaneController {
    private final String DUE_PREFIX = "Due: ";
    private final TaskModel model;

    @FXML
    private Button removeButton;
    @FXML
    private Button editButton;
    @FXML
    private Text headerText;
    @FXML
    private Text dueText;
    @FXML
    private PriorityPaneController priorityPane;

    public RecentItemPaneController(TaskModel model) {
        this.model = model;
    }

    public void initialize() {
        updateFields();
    }

    public void updateFields() {
        updateHeaderText();
        updateDueText();
        updatePriority();
    }

    private void updateHeaderText() {
        headerText.setText(model.getHeader());
    }

    private void updateDueText() {
        dueText.setText(DUE_PREFIX + model.getDueDate().format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")));
    }

    private void updatePriority() {
        priorityPane.setPriority(model.getPriority());
    }

    //TODO actionlisteners for buttons, removing task and recent from map
}
