package com.example.mentis.presentation;

import com.example.mentis.business.data.Voxel;
import com.example.mentis.business.logic.ValidationStatus;
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
    private final int gridPosition;

    public VoxelComponent(StackPane container, int gridPosition, double space, SimpleBooleanProperty renderProperty, Voxel voxel) {
        super();
        super.getPoints().setAll(
                0.0, 0.0,
                container.getWidth() * space, 0.0,
                container.getWidth() * space / 2.0, container.getHeight() * space
        );
        super.setStrokeWidth(1.0);

        this.space = space;
        this.voxel = voxel;
        this.container = container;
        this.gridPosition = gridPosition;
        setColors();
        renderProperty.addListener((observable, oldValue, newValue) -> updateTriangle(container));

        controller = new VoxelComponentController(this, voxel);
    }

    public void setColors() {
        switch (this.gridPosition) {
            case 0 -> {
                this.fillColor = voxel.getValidationStatus().getColor();
                this.strokeColor = voxel.getValidationStatus().getColor();
            }
            case 1 -> {
                if (voxel.getValidationStatus() == ValidationStatus.REFUTED) {
                    this.fillColor = Color.GRAY;
                    this.strokeColor = Color.GRAY;
                } else {
                    this.fillColor = voxel.getValidationStatus().getColor();
                    this.strokeColor = voxel.getValidationStatus().getColor();
                }
            }
            case 2 -> {
                this.fillColor = Color.PINK;
                this.strokeColor = Color.PINK;
            }
        }
        super.setFill(this.fillColor.deriveColor(0, 1, 1, 0.5));
        super.setStroke(this.strokeColor);
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

    public void setStroke(Color color) {
        if (gridPosition == 1 && !voxel.isSelected() && voxel.getValidationStatus() == ValidationStatus.REFUTED) {
            super.setStroke(Color.GRAY);
        } else {
            super.setStroke(color);
        }
    }

}
