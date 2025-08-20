package com.example.mentis.presentation;

import com.example.mentis.business.data.Voxel;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class VoxelComponent extends Polygon {

    private final double space;
    private Color fillColor;
    private Color strokeColor;
    private final Controller controller;
    private final Voxel voxel;
    private final StackPane container;

    public VoxelComponent(StackPane container, Color fillColor, Color strokeColor, double space, SimpleBooleanProperty renderProperty, Voxel voxel) {
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
        this.strokeColor = strokeColor;
        this.voxel = voxel;
        this.container = container;
        renderProperty.addListener((observable, oldValue, newValue) -> updateTriangle(container));

        controller = new VoxelComponentController(this, voxel);
    }

    private void updateTriangle(StackPane container) {
        super.getPoints().setAll(
                0.0, 0.0,
                container.getWidth() * space, 0.0,
                container.getWidth() * space / 2.0, container.getHeight() * space
        );
    }

    public StackPane getContainer() {
        return container;
    }

    public void animateHover(StackPane container, double factor, Color targetFill, int ms) {
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

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }
}
