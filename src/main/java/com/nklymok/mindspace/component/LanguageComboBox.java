package com.nklymok.mindspace.component;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.util.Pair;

import java.util.Locale;

public class LanguageComboBox extends JFXComboBox<Pair<String, Locale>> {

    private final ObservableList<Pair<String, Locale>> languageComboBoxItems = FXCollections.observableArrayList(
            new Pair<>("Default", Locale.forLanguageTag("en-US")),
            new Pair<>("English", Locale.forLanguageTag("en-US")),
            new Pair<>("Українська", Locale.forLanguageTag("uk-UA"))
    );


    public LanguageComboBox() {
        super();
        setItems(languageComboBoxItems);
        setCellFactory(comboBox -> new ListCell<Pair<String, Locale>>() {
            @Override
            protected void updateItem(Pair<String, Locale> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (pair != null) {
                    setText(pair.getKey());
                }
            }
        });

        setCellFactory(comboBox -> new ListCell<Pair<String, Locale>>() {
            @Override
            protected void updateItem(Pair<String, Locale> pair, boolean empty) {
                super.updateItem(pair, empty);
                if (pair != null) {
                    setText(pair.getKey());
                }
            }
        });
    }

    public Locale getLocale() {
        return getValue().getValue();
    }

    public void setLocale(Locale locale) {
        for (Pair<String, Locale> p : getItems()) {
            if (p.getValue().equals(locale)) {
                setValue(p);
                return;
            }
        }
    }
}
