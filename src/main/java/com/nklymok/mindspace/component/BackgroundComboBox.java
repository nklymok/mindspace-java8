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

public class BackgroundComboBox extends JFXComboBox<Pair<String, String>> implements Subscriber {

    private final ObservableList<Pair<String, String>> backgroundComboBoxItems = FXCollections.observableArrayList(
            new Pair<>("None", null),
            new Pair<>("City 1", "image1"),
            new Pair<>("City 2", "image2"),
            new Pair<>("Flowers 1", "image3"),
            new Pair<>("Flowers 2", "image4"),
            new Pair<>("Moon", "image5"),
            new Pair<>("Camping", "image6")
    );


    public BackgroundComboBox() {
        super();
        AppEventBus.register(this);
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
            if (pick == null) {
                return;
            } else {
                AppEventBus.post(new BackgroundUpdateEvent(pick));
            }
        });
    }

    public String getImage() {
        return getValue().getValue();
    }
}
