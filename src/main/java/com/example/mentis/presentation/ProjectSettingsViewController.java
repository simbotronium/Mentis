package com.example.mentis.presentation;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.UID;
import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ProjectSettingsViewController implements Controller {

    @FXML
    private Node root;
    @FXML
    private VBox newAreaOverlay;
    @FXML
    private TextField nameField;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private VBox areaBox;
    @FXML
    private ListView<Area> areaList;
    private FadeTransition showTransition;
    private FadeTransition hideTransition;

    public Node getRoot() {
        return root;
    }

    @FXML
    public void initialize() {
        showTransition = new FadeTransition(Duration.millis(200), newAreaOverlay);
        showTransition.setFromValue(0.0);
        showTransition.setToValue(1.0);

        hideTransition = new FadeTransition(Duration.millis(200), newAreaOverlay);
        hideTransition.setFromValue(1.0);
        hideTransition.setToValue(0.0);
        hideTransition.setOnFinished(e -> newAreaOverlay.setVisible(false));

        newAreaOverlay.setVisible(false);

        Manager.getInstance().currentProjectProperty().addListener((observable, oldValue, newValue)
                -> areaList.setItems(Manager.getInstance().getCurrentProject().getAreas()));
        areaList.setItems(Manager.getInstance().getCurrentProject().getAreas());
        areaList.setCellFactory(list -> new AreaListCell(area -> {
            Manager.getInstance().getCurrentProject().getAreas().remove(area);
        }));
    }

    public void onOk() {
        if (nameField.getText().matches("^[A-Za-z]+$")) {
            Manager.getInstance().getCurrentProject().addArea(new Area(UID.next(), colorPicker.getValue(), nameField.getText()));
            closeOverlay();
        } else {
            nameField.getStyleClass().add("input-error");
        }
    }

    public void onNewArea() {
        System.out.println("new area");
        newAreaOverlay.setVisible(true);
        showTransition.play();
    }

    public void closeOverlay() {
        hideTransition.play();
    }

}
