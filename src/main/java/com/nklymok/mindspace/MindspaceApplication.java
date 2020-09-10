package com.nklymok.mindspace;

import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.component.ResourceBundles;
import com.nklymok.mindspace.concurrent.DeadlineThread;
import com.nklymok.mindspace.connection.ConnectionManager;
import com.nklymok.mindspace.controller.EditStageController;
import com.nklymok.mindspace.eventsystem.*;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Locale;
import java.util.ResourceBundle;

public class MindspaceApplication extends Application {
    private TaskService taskService;

    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle bundle = ResourceBundles.getResourceBundle();
        root = FXMLLoader.load(getClass().getResource("/fxmls/root-pane.fxml"), bundle);
        BlurEffect.setTarget(root);
        stage = primaryStage;

        double DEFAULT_WIDTH = 800d;
        double DEFAULT_HEIGHT = 600d;

        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        stage.setOnCloseRequest(e -> {
            ConnectionManager.closeConnection();
        });
        stage.setTitle("Project MindSpace");
        stage.getIcons().add(new Image(getClass().getResource("/sprites/icon.png").toString()));
        stage.setScene(scene);
        stage.setMinWidth(DEFAULT_WIDTH);
        stage.setMinHeight(DEFAULT_HEIGHT);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        ConnectionManager.closeConnection();
    }

    public void initialize() {
        taskService = TaskServiceImpl.getInstance();
        createPreviouslyExistedTasks();
        StageEventManager eventManager = new StageEventManager();

        DeadlineThread deadlineThread = new DeadlineThread(taskService);
        deadlineThread.setDaemon(true);
        deadlineThread.start();
    }

    public void createPreviouslyExistedTasks() {
        for (TaskModel taskModel : taskService.findAll()) {
            AppEventBus.post(new TaskCreateEvent(taskModel));
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(ResourceBundles.getCurrentLocale());
        launch(args);
    }
}