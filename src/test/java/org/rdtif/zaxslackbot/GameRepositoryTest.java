package org.rdtif.zaxslackbot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

public class GameRepositoryTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void fileNamesNeverReturnsNull() {
        GameRepository repository = new GameRepository(createConfiguration());

        Stream<String> stream = repository.fileNames();

        assertThat(stream, notNullValue());
    }

    @Test
    public void fileNamesContainsAtLeastOneFile() {
        GameRepository repository = new GameRepository(createConfiguration());
        String fileName = RandomStringUtils.randomAlphanumeric(25);
        createFile(fileName);
        Stream<String> stream = repository.fileNames();
        assertThat(stream.anyMatch(name -> name.equals(fileName)), equalTo(true));
    }

    private File createFile(String fileName) {
        try {
            return temporaryFolder.newFile(fileName);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private ZaxSlackBotConfiguration createConfiguration() {
        Properties properties = new Properties();
        properties.setProperty("game-directory", temporaryFolder.getRoot().getAbsolutePath());
        return new ZaxSlackBotConfiguration(properties);
    }
}