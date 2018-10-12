package org.rdtif.zaxslackbot;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigurationFileExtension implements BeforeAllCallback, AfterAllCallback {
    private static final String CONFIGURATION_FILE_NAME = "configuration.properties";
    private static final String API_TOKEN_PROPERTY_NAME = "api-token";
    private static final String GAME_DIRECTORY_PROPERTY_NAME = "game-directory";
    private static final Path CONFIGURATION_FILE_PATH = Paths.get(CONFIGURATION_FILE_NAME);

    @Override
    public void beforeAll(ExtensionContext context) {
        createConfigurationFile(createConfigurationInstance("", ""));
    }

    private ZaxSlackBotConfiguration createConfigurationInstance(String apiToken, String gameDirectory) {
        Properties properties = createConfigurationProperties(apiToken, gameDirectory);
        return new ZaxSlackBotConfiguration(properties);
    }

    private Properties createConfigurationProperties(String apiToken, String gameDirectory) {
        Properties properties = new Properties();
        properties.setProperty(API_TOKEN_PROPERTY_NAME, apiToken);
        properties.setProperty(GAME_DIRECTORY_PROPERTY_NAME, gameDirectory);
        return properties;
    }

    private void createConfigurationFile(ZaxSlackBotConfiguration configuration) {
        String output = API_TOKEN_PROPERTY_NAME + "=" + configuration.getApiToken() + "\n" + GAME_DIRECTORY_PROPERTY_NAME + "=" + configuration.getGameDirectory() + "\n";
        try {
            Files.deleteIfExists(CONFIGURATION_FILE_PATH);
            Files.createFile(CONFIGURATION_FILE_PATH);
            Files.write(CONFIGURATION_FILE_PATH, output.getBytes());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Files.deleteIfExists(CONFIGURATION_FILE_PATH);
    }
}
