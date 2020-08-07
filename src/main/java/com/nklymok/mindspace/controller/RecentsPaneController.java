package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.model.TaskModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.TreeMap;

public class RecentsPaneController {
    private final Map<TaskModel, Node> modelToNode;

    @FXML
    VBox recentsPane;

    public RecentsPaneController() {
        modelToNode = new TreeMap<>();
    }

    public void addRecent(Node recentNode, TaskModel taskModel) {
        modelToNode.put(taskModel, recentNode);
        recentsPane.getChildren().add(recentNode);
    }
}
