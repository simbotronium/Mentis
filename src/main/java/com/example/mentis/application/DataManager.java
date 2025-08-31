package com.example.mentis.application;

import com.example.mentis.application.serialisation.ColorDeserializer;
import com.example.mentis.application.serialisation.ColorSerializer;
import com.example.mentis.application.serialisation.ObservableListDeserializer;
import com.example.mentis.business.data.Area;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.data.ProjectListEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

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
        entries.add(new ProjectListEntry(p.getName(), p.getID()));
    }

    public static Project getProjectById(long id) {
        return projects.get(id);
    }

    public static ObjectMapper m = new ObjectMapper();

    public static void readFromFile() {
        if (path == null) {
            System.out.println("could not load projects from file - no valid path set");
            return;
        }
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ObservableList.class, new ObservableListDeserializer<>(Member.class));
        module.addDeserializer(ObservableList.class, new ObservableListDeserializer<>(Area.class));
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        m.registerModule(module);

        try {
            HashMap<Long, Project> map = m.readValue(new File(path), new TypeReference<HashMap<Long, Project>>() {});
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread loadingThread = getLoadingThread();
        loadingThread.start();
    }

    private static Thread getLoadingThread() {
        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("starting task");
                projects = m.readValue(new File(path), new TypeReference<>() {});
                System.out.println("found " + projects.size() + " projects");

                for (Project p: projects.values()) {
                    Platform.runLater(() -> entries.add(new ProjectListEntry(p.getName(), p.getID())));
                    Thread.sleep(500);
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

            m.writerWithDefaultPrettyPrinter()
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
