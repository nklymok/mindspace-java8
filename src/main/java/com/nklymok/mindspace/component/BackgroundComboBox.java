package com.nklymok.mindspace.component;

import com.jfoenix.controls.JFXComboBox;
import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.BackgroundUpdateEvent;
import com.nklymok.mindspace.eventsystem.Subscriber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.util.Pair;

import java.util.Locale;
import java.util.ResourceBundle;

public class BackgroundComboBox extends JFXComboBox<Pair<String, String>> implements Subscriber {

    private final ResourceBundle resourceBundle;


    public BackgroundComboBox() {
        super();
        AppEventBus.register(this);
        resourceBundle = ResourceBundles.getResourceBundle();
        ObservableList<Pair<String, String>> backgroundComboBoxItems = FXCollections.observableArrayList(
                new Pair<>(resourceBundle.getString("settings_bg_none"), null),
                new Pair<>(resourceBundle.getString("settings_bg_i1"), "image1"),
                new Pair<>(resourceBundle.getString("settings_bg_i2"), "image2"),
                new Pair<>(resourceBundle.getString("settings_bg_i3"), "image3"),
                new Pair<>(resourceBundle.getString("settings_bg_i4"), "image4"),
                new Pair<>(resourceBundle.getString("settings_bg_i5"), "image5"),
                new Pair<>(resourceBundle.getString("settings_bg_i6"), "image6")
        );
        setItems(backgroundComboBoxItems);
        setCellFactory(comboBox -> new ListCell<Pair<String, String>>() {
            @Override
            protected void updateItem(Pair<String, String> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (pair != null) {
                    setText(pair.getKey());
                }
            }
        });

        setCellFactory(comboBox -> new ListCell<Pair<String, String>>() {
            @Override
            protected void updateItem(Pair<String, String> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (pair != null) {
                    setText(pair.getKey());
                }
            }
        });

        valueProperty().addListener((obs, oldVal, newVal) -> {
            String pick = newVal.getValue();
                AppEventBus.post(new BackgroundUpdateEvent(pick));
        });
    }

    public String getImage() {
        return getValue().getValue();
    }
}
