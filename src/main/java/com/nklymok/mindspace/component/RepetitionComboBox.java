package com.nklymok.mindspace.component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;

public class RepetitionComboBox extends ComboBox<Repeats> {
    ObservableList<Repeats> repetitionComboBoxItems = FXCollections.observableArrayList(
            Repeats.values()
    );

    public RepetitionComboBox() {
        super();
        setItems(repetitionComboBoxItems);
    }
}
