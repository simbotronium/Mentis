package com.example.mentis.presentation.components;

import com.example.mentis.business.data.Area;
import com.example.mentis.presentation.transitions.DeleteButtonFadeTransition;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.function.Consumer;

public class AreaListCell extends ListCell<Area> {

    private final HBox root;
    private final Label nameLabel;
    private final Circle circle;
    private final Button delete;
    private final Consumer<Area> onDelete;

    private FadeTransition fade;
    private final DeleteButtonFadeTransition deleteButtonFadeTransition;

    public AreaListCell(Consumer<Area> onDelete) {
        this.onDelete = onDelete;

        root = new HBox();
        nameLabel = new Label("ERROR");
        circle = new Circle(15);
        delete = new Button("\u2716");
        deleteButtonFadeTransition = new DeleteButtonFadeTransition(delete);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        delete.getStyleClass().add("delete-button");
        delete.setOpacity(0);
        circle.setStyle("-fx-border-width: 2; -fx-border-color: black");
        circle.setFill(Color.GREY);
        nameLabel.getStyleClass().add("standard-text");
        root.setPadding(new Insets(10));
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER_LEFT);
        root.getStyleClass().add("elementContainer");

        root.getChildren().addAll(circle, nameLabel, region, delete);

        this.hoverProperty().addListener((observable, was, is) -> animateDelete(is));
    }

    private void animateDelete(boolean show) {
        deleteButtonFadeTransition.play(show);
    }

    @Override
    protected void updateItem(Area area, boolean empty) {
        super.updateItem(area, empty);

        if (empty || area == null) {
            setGraphic(null);
            setText(null);
        } else {
            nameLabel.setText(area.name());
            circle.setFill(area.color());
            delete.setOnAction(e -> onDelete.accept(area));
            setGraphic(root);
            setText(null);
        }
    }

    public Button getDelete() {
        return delete;
    }
}
