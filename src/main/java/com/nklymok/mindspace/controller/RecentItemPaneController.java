package com.nklymok.mindspace.controller;

import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.Subscriber;
import com.nklymok.mindspace.eventsystem.TaskDeleteEvent;
import com.nklymok.mindspace.eventsystem.TaskUpdateEvent;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RecentItemPaneController implements Comparable<RecentItemPaneController>, Initializable, Subscriber {
    private final String DUE_PREFIX = "Due: ";
    private TaskModel model;

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

    private final EventHandler<ActionEvent> removeButtonHandler = event -> {
        AppEventBus.post(new TaskDeleteEvent(model));
    };

    private final EventHandler<ActionEvent> editButtonHandler = event -> edit();

    public RecentItemPaneController(TaskModel model) {
        this.model = model;
    }

    private void edit() {
        FXMLLoader editStageFXMLLoader = new FXMLLoader(getClass().getResource("/fxmls/edit-stage.fxml"));
        EditStageController editStageController = new EditStageController(model);
        editStageFXMLLoader.setController(editStageController);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        try {
            Scene editScene = new Scene(editStageFXMLLoader.load());
            editScene.setFill(Color.TRANSPARENT);
            stage.setScene(editScene);
            stage.setOnShown(event -> BlurEffect.getInstance().blur());
            stage.setOnHidden(event -> BlurEffect.getInstance().unblur());
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    private void handleTaskUpdateEvent(TaskUpdateEvent event) {
        TaskModel eventModel = event.getModel();
        if(this.model.getId().equals(eventModel.getId())) {
            this.model = eventModel;
        }

        updateFields();
    }

    private void updateFields() {
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

    public TaskModel getModel() {
        return model;
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);

        updateFields();
        removeButton.setOnAction(removeButtonHandler);
        editButton.setOnAction(editButtonHandler);
    }

    @Override
    public int compareTo(RecentItemPaneController recent) {
        System.out.println("sorting recent");
        return 0;
    }
}
