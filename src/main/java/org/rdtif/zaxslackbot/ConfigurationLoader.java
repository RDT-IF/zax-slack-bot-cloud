package org.rdtif.zaxslackbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class ConfigurationLoader {
    private static final String CONFIGURATION_FILE_NAME = "configuration.properties";

    ZaxSlackBotConfiguration getConfigurationFrom(String directoryPath) {
        Properties properties = new Properties();
        String filePath = directoryPath + File.separator + CONFIGURATION_FILE_NAME;

        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return new ZaxSlackBotConfiguration(properties);
    }
}
