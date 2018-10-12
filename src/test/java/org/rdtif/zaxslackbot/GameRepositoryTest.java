package org.rdtif.zaxslackbot;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

class GameRepositoryTest {
    private final String GAME_DIRECTORY = RandomStringUtils.randomAlphabetic(10);

    @BeforeEach
    void beforeEach() throws IOException {
        Files.createDirectory(Paths.get(GAME_DIRECTORY));
    }

    @AfterEach
    void afterEach() throws IOException {
        //noinspection ResultOfMethodCallIgnored
        Files.walk(Paths.get(GAME_DIRECTORY))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void fileNamesNeverReturnsNull() {
        GameRepository repository = new GameRepository(createConfiguration(GAME_DIRECTORY));

        Collection<String> stream = repository.fileNames();

        assertThat(stream, notNullValue());
    }

    @Test
    void fileNamesContainsOneFile() {
        GameRepository repository = new GameRepository(createConfiguration(GAME_DIRECTORY));
        String fileName = RandomStringUtils.randomAlphanumeric(25) + ".z8";
        createFile(GAME_DIRECTORY + "/" + fileName);

        Collection<String> names = repository.fileNames();

        assertThat(names, hasItem(fileName));
    }

    @ValueSource(ints = {1, 2, 3, 4, 5, 7, 8})
    @ParameterizedTest
    void onlyValidExtensions(int version) {
        GameRepository repository = new GameRepository(createConfiguration(GAME_DIRECTORY));
        String fileName = RandomStringUtils.randomAlphanumeric(5) + ".z" + version;
        String otherFileName = RandomStringUtils.randomAlphanumeric(5) + ".txt";
        createFile(GAME_DIRECTORY + "/" + fileName);
        createFile(GAME_DIRECTORY + "/" + otherFileName);

        Collection<String> names = repository.fileNames();

        assertThat(names, hasItem(fileName));
        assertThat(names, not(hasItem(otherFileName)));
    }

    @Test
    void includeSubDirectories() {
        GameRepository repository = new GameRepository(createConfiguration(GAME_DIRECTORY));
        String subDirectory = RandomStringUtils.randomAlphabetic(13);
        String fileName = RandomStringUtils.randomAlphanumeric(25) + ".z8";
        createDirectory(GAME_DIRECTORY + "/" + subDirectory);
        createFile(GAME_DIRECTORY + "/" + subDirectory + "/" + fileName);

        Collection<String> names = repository.fileNames();

        assertThat(names, hasItem(fileName));
    }

    private void createFile(String filePath) {
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void createDirectory(String directoryPath) {
        try {
            Files.createDirectory(Paths.get(directoryPath));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private ZaxSlackBotConfiguration createConfiguration(String gameDirectory) {
        Properties properties = new Properties();
        properties.setProperty("game-directory", gameDirectory);
        return new ZaxSlackBotConfiguration(properties);
    }
}
