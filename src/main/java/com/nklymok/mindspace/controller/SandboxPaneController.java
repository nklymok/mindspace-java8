package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.model.TaskModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.Map;
import java.util.TreeMap;

public class SandboxPaneController {
    private final Map<TaskModel, Node> modelToNode;

    @FXML
    private BorderPane sandboxPane;

    public SandboxPaneController() {
        modelToNode = new TreeMap<>();
    }

    public void addTask(Node taskNode, TaskModel taskModel) {
        modelToNode.put(taskModel, taskNode);
        sandboxPane.getChildren().add(taskNode);
    }

    public void removeTask(TaskModel taskModel) {
        modelToNode.remove(taskModel);
    }

    public Node getTask(TaskModel model) {
        return modelToNode.get(model);
    }
}
