package com.example.mentis.application;

import com.example.mentis.application.serialisation.*;
import com.example.mentis.business.data.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class DataManager {

    private static String path;
    private static HashMap<Long, Project> projects = new HashMap<>();
    private static final ObservableList<ProjectListEntry> entries = FXCollections.observableArrayList();
    private static final Logger log = LoggerFactory.getLogger(DataManager.class);

    public static void confirmProject(Project p) {
        projects.put(p.getID(), p);
        entries.add(new ProjectListEntry(p.getName(), p.getID()));
    }

    public static Project getProjectById(long id) {
        return projects.get(id);
    }

    public static ObjectMapper m = new ObjectMapper();

    public static void readFromFile() {
        if (path == null) {
            log.info("could not load projects from file - no valid path set");
            return;
        }
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ObservableList.class, new ObservableListDeserializer<>());
        module.addDeserializer(SimpleBooleanProperty.class, new SimpleBooleanPropertyDeserializer());
        module.addDeserializer(SimpleLongProperty.class, new SimpleLongPropertyDeserializer());
        module.addDeserializer(SimpleIntegerProperty.class, new SimpleIntegerPropertyDeserializer());
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        m.registerModule(module);

        Thread loadingThread = getLoadingThread();
        loadingThread.start();
    }

    private static Thread getLoadingThread() {
        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                log.info("starting task");
                projects = m.readValue(new File(path), new TypeReference<>() {});
                log.info("found " + projects.size() + " projects");

                for (Project p: projects.values()) {
                    Platform.runLater(() -> entries.add(new ProjectListEntry(p.getName(), p.getID())));
                }

                return null;
            }
        };

        Thread loadingThread = new Thread(loadingTask);
        loadingThread.setDaemon(true);
        return loadingThread;
    }

    public static void saveToFile() {

        try {
            if (path == null) throw new IOException("no valid path set");

            m.writerWithDefaultPrettyPrinter().writeValue(new File(path), projects);

            log.info("updatet save file");
        } catch (IOException e) {
            log.error("could not save - " + e.getMessage());
        }
    }

    public static void createDataFolderIfMissing() {

        String basePath = System.getProperty("user.home");
        Path dataPath = Path.of(basePath, "mentisData");

        if (Files.notExists(dataPath)) {
            try {
                Files.createDirectories(dataPath);
                log.info("created data folder: " + dataPath);
            } catch (Exception e) {
                log.error("error while creating data folder: " + e.getMessage());
            }
        } else {
            log.info("data folder found: " + dataPath);
        }

        path = dataPath.toString() + "/save_file.json";
    }

    public static ObservableList<ProjectListEntry> getEntries() {
        return entries;
    }

}
