package com.example.mentis.presentation;

import com.example.mentis.application.MentisApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class MainViewController {

    private final Pane root = new StackPane();

    private static final MainViewController instance = new MainViewController();

    private MainViewController() {
        try {
            changeView(FXMLLoader.load(Objects.requireNonNull(MentisApplication.class.getResource("/com/example/mentis/views/main-menu-view.fxml"))));
        } catch (IOException e) {
            System.out.println("something went wrong while setting up MainViewController:\n");
            e.printStackTrace();
        }
    }

    public void changeView(Parent view) {
        root.getChildren().setAll(view);
    }

    public static MainViewController getInstance() {
        return instance;
    }

    public Pane getRoot() {
        return root;
    }
}