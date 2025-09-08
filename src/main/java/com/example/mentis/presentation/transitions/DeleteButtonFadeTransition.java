package com.example.mentis.presentation.transitions;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.util.Duration;

public class DeleteButtonFadeTransition {

    private final Node node;
    private FadeTransition fade;

    public DeleteButtonFadeTransition(Node node) {
        this.node = node;
    }

    public void play(boolean show) {
        if (fade != null) fade.stop();
        fade = new FadeTransition(Duration.millis(180), node);
        fade.setFromValue(node.getOpacity());
        fade.setToValue(show ? 1 : 0);
        fade.setInterpolator(Interpolator.EASE_BOTH);
        fade.play();
    }
}
