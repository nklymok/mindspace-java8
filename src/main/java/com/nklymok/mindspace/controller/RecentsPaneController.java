package com.nklymok.mindspace.controller;

import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.Subscriber;
import com.nklymok.mindspace.eventsystem.TaskCreateEvent;
import com.nklymok.mindspace.eventsystem.TaskDeleteEvent;
import com.nklymok.mindspace.model.TaskModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class RecentsPaneController implements Initializable, Subscriber {
    private final Map<TaskModel, Node> modelToNode;

    @FXML
    VBox recentsPane;

    public RecentsPaneController() {
        modelToNode = new TreeMap<>();
    }

    private void addRecent(Node recentNode, TaskModel taskModel) {
        modelToNode.put(taskModel, recentNode);
        recentsPane.getChildren().add(recentNode);
    }

    private void removeRecent(TaskModel model) {
        recentsPane.getChildren().remove(modelToNode.get(model));
        modelToNode.remove(model);
    }

    @Subscribe
    public void handleTaskCreateEvent(TaskCreateEvent event) {
        if (event.getTaskParent() != null) {
            addRecent(event.getRecentParent(), event.getModel());
        }
    }

    @Subscribe
    private void handleTaskDeleteEvent(TaskDeleteEvent event) {
        removeRecent(event.getModel());
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);
    }
}
