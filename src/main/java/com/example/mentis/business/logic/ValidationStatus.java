package com.example.mentis.business.logic;

import javafx.scene.paint.Color;

public enum ValidationStatus {

    VALID(Color.GREEN),
    REFUTED(Color.RED),
    UNDECIDED(Color.ORANGE);

    private final Color color;

    private ValidationStatus(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
