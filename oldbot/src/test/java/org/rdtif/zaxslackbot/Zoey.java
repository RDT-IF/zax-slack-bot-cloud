package org.rdtif.zaxslackbot;

import org.rdtif.zaxslackbot.userinterface.Extent;
import org.rdtif.zaxslackbot.userinterface.Position;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;
import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class Zoey {
    private static final String GAME_DIRECTORY_PROPERTY_NAME = "game-directory";

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

    public static void deleteDirectory(String directoryPath) {
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

    public static Extent randomExtent() {
        return new Extent(new Random().nextInt(40), new Random().nextInt(40));
    }

    public static Position randomPosition() {
        return new Position(new Random().nextInt(50), new Random().nextInt(50));
    }
}
