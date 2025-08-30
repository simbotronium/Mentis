package com.example.mentis.application;

import com.example.mentis.business.data.Project;
import com.example.mentis.business.data.ProjectListEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class DataManager {

    private static String path;
    private static HashMap<Long, Project> projects = new HashMap<>();
    private static final ObservableList<ProjectListEntry> entries = FXCollections.observableArrayList();

    public static void confirmProject(Project p) {
        projects.put(p.getID(), p);
    }

    public static Project getProjectById(long id) {
        return projects.get(id);
    }

    public static void readFromFile() {
        if (path == null) {
            System.out.println("could not load projects from file - no valid path set");
            return;
        }
        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("starting task");
                ObjectMapper mapper = new ObjectMapper();
                projects = mapper.readValue(new File(path), new TypeReference<>() {});
                System.out.println(projects.size());

                return null;
            }
        };

        Thread loadingThread = new Thread(loadingTask);
        loadingThread.setDaemon(true);
        loadingThread.start();
    }

    public static void saveToFile() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (path == null) throw new IOException("no valid path set");

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(path), projects);

        } catch (IOException e) {
            System.out.println("could not save - " + e.getMessage());
        }
    }

    public static void createDataFolderIfMissing() {

        String basePath = System.getProperty("user.dir");
        Path dataPath = Path.of(basePath, "mentisData");

        if (Files.notExists(dataPath)) {
            try {
                Files.createDirectories(dataPath);
                System.out.println("created data folder: " + dataPath);
            } catch (Exception e) {
                System.err.println("error while creating data folder: " + e.getMessage());
            }
        } else {
            System.out.println("data folder found: " + dataPath);
        }

        path = dataPath.toString() + "/save_file.json";
    }

    public static ObservableList<ProjectListEntry> getEntries() {
        return entries;
    }

}
