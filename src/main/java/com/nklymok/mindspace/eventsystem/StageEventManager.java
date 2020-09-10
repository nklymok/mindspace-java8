package com.nklymok.mindspace.eventsystem;

import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.component.ResourceBundles;
import com.nklymok.mindspace.controller.EditStageController;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ResourceBundle;

public class StageEventManager implements Subscriber {

    public StageEventManager() {
        AppEventBus.register(this);
    }

    @Subscribe
    public static void handleTaskEditEvent(TaskEditEvent event) {
        ResourceBundle resourceBundle = ResourceBundles.getResourceBundle();
        FXMLLoader editStageFXMLLoader = new FXMLLoader(
                TaskEditEvent.class.getResource("/fxmls/edit-stage.fxml"),
                resourceBundle);

        EditStageController editStageController = new EditStageController(event.getModel());
        editStageFXMLLoader.setController(editStageController);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        try {
            Scene editScene = new Scene(editStageFXMLLoader.load());
            editScene.setFill(Color.TRANSPARENT);
            stage.setScene(editScene);
            stage.setOnShown(e -> BlurEffect.getInstance().blur());
            stage.setOnHidden(e -> BlurEffect.getInstance().unblur());
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
