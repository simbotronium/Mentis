package com.example.mentis.presentation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Triangle extends Polygon {

    private final double space;
    private Color fillColor;

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
        this.fillColor = fillColor;
        renderProperty.addListener((observable, oldValue, newValue) -> updateTriangle(container));

        setupHoverEffect(container);
    }

    private void updateTriangle(StackPane container) {
        super.getPoints().setAll(
                0.0, 0.0,
                container.getWidth() * space, 0.0,
                container.getWidth() * space / 2.0, container.getHeight() * space
        );
    }

    private void setupHoverEffect(StackPane container) {
        container.setOnMouseEntered(e -> animateHover(container, 2.0, fillColor.deriveColor(0, 1, 1, 0.8), 150));
        container.setOnMouseExited(e -> animateHover(container, 1, fillColor.deriveColor(0, 1, 1, 0.5), 200));
    }

    private void animateHover(StackPane container, double factor, Color targetFill, int ms) {
        double dy = (container.getHeight() * space) * (factor - 1);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(ms),
                        new KeyValue(scaleXProperty(), factor),
                        new KeyValue(scaleYProperty(), factor),
                        new KeyValue(translateYProperty(), -dy),
                        new KeyValue(fillProperty(), targetFill, Interpolator.EASE_BOTH)
                )
        );
        timeline.play();
    }

}
