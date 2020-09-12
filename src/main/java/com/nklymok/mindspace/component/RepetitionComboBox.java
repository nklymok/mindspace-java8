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

    public int getRepeats() {
        if (getValue() == null) {
            return 0;
        }

        switch (getValue()) {
            case Daily: return 1;
            case Weekly: return 2;
            case Monthly: return 3;
            case Yearly: return 4;
            default: return 0;
        }
    }
}
