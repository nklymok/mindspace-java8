package com.nklymok.mindspace.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.Pair;

public class PriorityComboBox extends ComboBox<Pair<String, Integer>> {

    private final ObservableList<Pair<String, Integer>> priorityComboBoxItems = FXCollections.observableArrayList(
            new Pair<>("None",-1),
            new Pair<>("Low", 0),
            new Pair<>("Medium",1),
            new Pair<>("High", 2)
    );


    public PriorityComboBox() {
        super();
        setItems(priorityComboBoxItems);
        setCellFactory(comboBox -> new ListCell<Pair<String, Integer>>() {
            @Override
            protected void updateItem(Pair<String, Integer> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (pair != null) {
                    setText(pair.getKey());
                }
            }
        });

        setCellFactory(comboBox -> new ListCell<Pair<String, Integer>>() {
            @Override
            protected void updateItem(Pair<String, Integer> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (pair != null) {
                    setText(pair.getKey());
                }
            }
        });
    }

    public int getPriority() {
        return getValue().getValue();
    }

    public void setPriority(int priority) {
        for (Pair p : getItems()) {
            if (p.getValue().equals(priority)) {
                setValue(p);
                return;
            }
        }
    }
}
