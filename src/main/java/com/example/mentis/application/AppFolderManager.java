package com.example.mentis.application;

import java.nio.file.Files;
import java.nio.file.Path;

public class AppFolderManager {

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
    }

}
