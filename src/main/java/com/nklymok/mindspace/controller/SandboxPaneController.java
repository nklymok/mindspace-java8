package com.nklymok.mindspace.controller;

import animatefx.animation.AnimationFX;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInDown;
import animatefx.animation.FadeInRight;
import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.eventsystem.*;
import com.nklymok.mindspace.model.TaskModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
// TODO bugfix when removing task 1, task 2 is removed (Fix removing)
public class SandboxPaneController implements Initializable, Subscriber {
    private final Map<TaskModel, Node> modelToNode;

    @FXML
    private BorderPane sandboxPane;

    private final double TASK_WIDTH = 250;
    private final double TASK_HEIGHT = 100;

    public SandboxPaneController() {
        modelToNode = new TreeMap<>();
    }

    private void addTask(Node taskNode, TaskModel taskModel) {
        modelToNode.put(taskModel, taskNode);
        sandboxPane.getChildren().add(taskNode);

        // setting positioning listeners to protect from leaving the scene boundaries
        taskNode.translateXProperty().addListener(e -> relocateTaskNode(taskNode));
        taskNode.translateYProperty().addListener(e -> relocateTaskNode(taskNode));
    }

    private void removeTask(TaskModel model) {
        sandboxPane.getChildren().remove(modelToNode.get(model));
        modelToNode.remove(model);
    }

    @Subscribe
    private void handleTaskCreateEvent(TaskCreateEvent event) {
        if (event.getTaskParent() != null) {
            addTask(event.getTaskParent(), event.getModel());
        }
    }

//    @Subscribe void handleTaskAnimationEvent(TaskAnimationEvent event) {
//        AnimationFX animation;
//        TaskModel eventModel = event.getModel();
//        Node node = null;
//
//        for (Map.Entry<TaskModel, Node> e : modelToNode.entrySet()) {
//            if (e.getKey().equals(eventModel)) {
//                node = e.getValue();
//                break;
//            }
//        }
//
//        if (node != null) {
//            animation = new FadeInDown(node).setSpeed(5d);
//            animation.play();
//        }
//    }

    @Subscribe
    private void handleTaskDeleteEvent(TaskDeleteEvent event) {
        removeTask(event.getModel());
    }

    private void relocateTaskNode(Node taskNode) {
        int parameter = legalCheck(taskNode.getTranslateX(), taskNode.getTranslateY());
        switch (parameter) {
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
    }

    private int legalCheck(double x, double y) {
        if (sandboxPane.widthProperty().doubleValue() <= 0 || sandboxPane.heightProperty().doubleValue() <= 0) {
            return 0;
        }

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

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);

        sandboxPane.widthProperty().addListener(e -> {
            for (Map.Entry<TaskModel, Node> item : modelToNode.entrySet()) {
                relocateTaskNode(item.getValue());
            }
        });
        sandboxPane.heightProperty().addListener(e -> {
            for (Map.Entry<TaskModel, Node> item : modelToNode.entrySet()) {
                relocateTaskNode(item.getValue());
            }
        });
    }
}
