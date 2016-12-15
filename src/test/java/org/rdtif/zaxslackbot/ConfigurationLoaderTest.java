package org.rdtif.zaxslackbot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigurationLoaderTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private final ConfigurationLoader configurationLoader = new ConfigurationLoader();

    @Test
    public void neverReturnNull() {
        createConfigurationFile("");
        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(temporaryFolder.getRoot().getPath());

        assertThat(configuration, notNullValue());
    }

    @Test
    public void tokenProperty() {
        String token = RandomStringUtils.randomAlphanumeric(25);
        createConfigurationFile(token);

        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(temporaryFolder.getRoot().getPath());

        assertThat(configuration.getApiToken(), equalTo(token));

    }

    private void createConfigurationFile(String token) {
        try {
            File file = temporaryFolder.newFile("configuration.properties");
            writeProperties(file.getAbsolutePath(), token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeProperties(String file, String token) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file))) {
            writer.write("api-token=" + token);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}