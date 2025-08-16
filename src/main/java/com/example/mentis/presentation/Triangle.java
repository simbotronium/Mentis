package com.example.mentis.presentation;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Triangle extends Polygon {

    private final double space;

    public Triangle(StackPane container, Color fillColor, Color strokeColor, double space, SimpleBooleanProperty renderProperty) {
        super();
        super.getPoints().setAll(
                0.0, 0.0,
                container.getWidth() * space, 0.0,
                container.getWidth() * space / 2.0, container.getHeight() * space
        );
        super.setFill(fillColor.deriveColor(0, 1, 1, 0.5));
        super.setStroke(strokeColor);
        super.setStrokeWidth(1.0);

        this.space = space;
        renderProperty.addListener((observable, oldValue, newValue) -> updateTriangle(container));
    }

    private void updateTriangle(StackPane container) {
        super.getPoints().setAll(
                0.0, 0.0,
                container.getWidth() * space, 0.0,
                container.getWidth() * space / 2.0, container.getHeight() * space
        );
    }

}
