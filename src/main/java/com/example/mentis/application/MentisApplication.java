package com.example.mentis.application;

import com.example.mentis.presentation.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MentisApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(MainViewController.getInstance().getRoot(), 1920, 1080);
        stage.setTitle("Mentis");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}