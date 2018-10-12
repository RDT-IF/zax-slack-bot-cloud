package org.rdtif.zaxslackbot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;

public class Zoey {
    private static final String CONFIGURATION_FILE_NAME = "configuration.properties";
    private static final String GAME_DIRECTORY_PROPERTY_NAME = "game-directory";

    public static void createConfigurationFile() {
        createFile(CONFIGURATION_FILE_NAME);
    }

    public static void createFile(String filePath) {
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void createDirectory(String directoryPath) {
        try {
            Files.createDirectory(Paths.get(directoryPath));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static ZaxSlackBotConfiguration createConfigurationInstance(String gameDirectory) {
        Properties properties = new Properties();
        properties.setProperty(GAME_DIRECTORY_PROPERTY_NAME, gameDirectory);
        return new ZaxSlackBotConfiguration(properties);
    }

    public static void deleteConfigurationFile() {
        deleteFile(CONFIGURATION_FILE_NAME);
    }

    public static void deleteFile(String filePath) {
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void deleteDirectory(String directoryPath)  {
        try {
            //noinspection ResultOfMethodCallIgnored
            Files.walk(Paths.get(directoryPath))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
