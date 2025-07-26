package com.example.mentis.business.logic;

import javafx.beans.property.SimpleObjectProperty;

public class Manager {

    private static final Manager instance = new Manager();
    private SimpleObjectProperty<View> currentView = new SimpleObjectProperty<>();

    private Manager() {}

    public static Manager getInstance() {
        return instance;
    }

    public SimpleObjectProperty<View> currentViewProperty() {
        return currentView;
    }
}
