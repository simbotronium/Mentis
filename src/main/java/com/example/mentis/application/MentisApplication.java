package com.example.mentis.application;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.example.mentis.presentation.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class MentisApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(MentisApplication.class);

    @Override
    public void start(Stage stage) {
        setUpLogging();
        DataManager.createDataFolderIfMissing();
        log.warn("starting application on " + LocalDateTime.now());
        DataManager.readFromFile();
        // TxtParser.testParse();

        ViewManager mainController = ViewManager.getInstance();
        Scene scene = new Scene(mainController.getRoot(), 1920, 1080);
        stage.setOnCloseRequest(e -> DataManager.saveToFile());
        stage.setTitle("Mentis");
        stage.setScene(scene);
        stage.show();
    }

    private void setUpLogging() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(MentisApplication.class.getResourceAsStream( "/logback.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}