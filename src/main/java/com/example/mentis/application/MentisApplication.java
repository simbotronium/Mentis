package com.example.mentis.application;

import com.example.mentis.presentation.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MentisApplication extends Application {
    @Override
    public void start(Stage stage) {
        DataManager.createDataFolderIfMissing();
        DataManager.readFromFile();

        ViewManager mainController = ViewManager.getInstance();
        Scene scene = new Scene(mainController.getRoot(), 1920, 1080);
        stage.setOnCloseRequest(e -> DataManager.saveToFile());
        stage.setTitle("Mentis");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}