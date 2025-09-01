package com.example.mentis.presentation;

import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class ViewManager implements Controller {

    private final Pane root = new StackPane();
    private final Manager model = Manager.getInstance();
    private final Map<View, Controller> cachedControllers = new HashMap<>();
    private FXMLLoader loader = new FXMLLoader();

    public ViewManager() {
        model.currentViewProperty().addListener(((observable, oldValue, newValue) -> {
            changeView(newValue);
        }));

        model.currentViewProperty().set(View.MAIN_MENU);
    }

    public void changeView(View view) {
        if (cachedControllers.containsKey(view)) {
            root.getChildren().setAll(cachedControllers.get(view).getRoot());
            return;
        }

        try {
            loader = new FXMLLoader(getClass().getResource(view.getFxmlPath()));
            Parent newRoot = loader.load();
            Controller controller = loader.getController();
            cachedControllers.put(view, controller);
            root.getChildren().setAll(newRoot);
        } catch (Exception e) {
            System.out.println("Something went wrong while loading View");
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }
}