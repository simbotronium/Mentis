package com.example.mentis.presentation;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ViewManager implements Controller {

    private final Pane root = new StackPane();
    private final Map<View, Controller> cachedControllers = new HashMap<>();
    private FXMLLoader loader = new FXMLLoader();
    private final Logger log = LoggerFactory.getLogger(ViewManager.class);

    private static final ViewManager instance = new ViewManager();

    public static ViewManager getInstance() {
        return instance;
    }

    private ViewManager() {
        changeView(View.MAIN_MENU);
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
            log.error("Something went wrong while loading View: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }
}