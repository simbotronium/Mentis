package com.example.mentis.presentation.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class InfoComponent extends StackPane {
    private final TextField textField;
    private final int index;
    private final Label label;

    public InfoComponent(int index) {
        this.setAlignment(Pos.TOP_LEFT);
        this.index = index;
        textField = new TextField();
        label = new Label("");
        label.getStyleClass().add("standard-text");
        label.setVisible(false);
        this.getChildren().addAll(textField, label);
    }

    public TextField getTextField() {
        return textField;
    }

    public Label getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }
}
