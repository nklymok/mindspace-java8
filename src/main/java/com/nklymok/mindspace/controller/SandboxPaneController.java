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

    private final double TASK_WIDTH = 250;
    private final double TASK_HEIGHT = 100;

    public SandboxPaneController() {
        modelToNode = new TreeMap<>();
    }

    public void addTask(Node taskNode, TaskModel taskModel) {
        modelToNode.put(taskModel, taskNode);
        sandboxPane.getChildren().add(taskNode);

        // setting positioning listeners to protect from leaving the scene boundaries
        taskNode.translateXProperty().addListener(e -> {
            switch (legalCheck(taskNode.getTranslateX(), taskNode.getTranslateY())) {
                case 0:
                    return;
                case -1:
                    taskNode.setTranslateX(0);
                    break;
                case -2:
                    taskNode.setTranslateX(sandboxPane.widthProperty().doubleValue());
                    break;
                case -3:
                    taskNode.setTranslateY(0);
                    break;
                case -4:
                    taskNode.setTranslateY(sandboxPane.heightProperty().doubleValue());
                    break;
            }
        });
        taskNode.translateYProperty().addListener(e -> {
            switch (legalCheck(taskNode.getTranslateX(), taskNode.getTranslateY())) {
                case 0:
                    return;
                case -1:
                    taskNode.setTranslateX(0);
                    break;
                case -2:
                    taskNode.setTranslateX(sandboxPane.widthProperty().doubleValue() - TASK_WIDTH);
                    break;
                case -3:
                    taskNode.setTranslateY(0);
                    break;
                case -4:
                    taskNode.setTranslateY(sandboxPane.heightProperty().doubleValue() - TASK_HEIGHT);
                    break;
            }
        });
    }

    public void removeTask(TaskModel model) {
        sandboxPane.getChildren().remove(modelToNode.get(model));
        modelToNode.remove(model);
    }

    public Node getTask(TaskModel model) {
        return modelToNode.get(model);
    }

    private int legalCheck(double x, double y) {
        if (x < 0) {
            return -1;
        }

        if (x > sandboxPane.widthProperty().doubleValue() - TASK_WIDTH) {
            return -2;
        }

        if (y < 0) {
            return -3;
        }

        if (y > sandboxPane.heightProperty().doubleValue() - TASK_HEIGHT) {
            return -4;
        }

        return 0;
    }
}
