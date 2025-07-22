package com.example.mentis.presentation;

import com.example.mentis.application.MentisApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class MainMenuViewController {

    @FXML
    protected void onSomeProject() {
        try {
            MainViewController mainViewController = MainViewController.getInstance();
            assert mainViewController != null;
            Parent view = FXMLLoader.load(Objects.requireNonNull(
                    MentisApplication.class.getResource("/com/example/mentis/views/project-view.fxml")
            ));
            mainViewController.changeView(view);
        } catch (IOException e) {
            System.out.println("something went wrong while loading project-view.fxml:\n");
            e.printStackTrace();
        }
    }

}
