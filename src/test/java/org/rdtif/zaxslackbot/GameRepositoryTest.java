package org.rdtif.zaxslackbot;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

class GameRepositoryTest {
    private final String GAME_DIRECTORY = RandomStringUtils.randomAlphabetic(10);

    @BeforeEach
    void beforeEach() {
        Zoey.createDirectory(GAME_DIRECTORY);
    }

    @AfterEach
    void afterEach() {
        Zoey.deleteDirectory(GAME_DIRECTORY);
    }

    @Test
    void fileNamesNeverReturnsNull() {
        GameRepository repository = new GameRepository(Zoey.createConfigurationInstance(GAME_DIRECTORY));

        Collection<String> stream = repository.fileNames();

        assertThat(stream, notNullValue());
    }

    @Test
    void fileNamesContainsOneFile() {
        GameRepository repository = new GameRepository(Zoey.createConfigurationInstance(GAME_DIRECTORY));
        String fileName = RandomStringUtils.randomAlphanumeric(25) + ".z8";
        Zoey.createFile(GAME_DIRECTORY + "/" + fileName);

        Collection<String> names = repository.fileNames();

        assertThat(names, hasItem(fileName));
    }

    @ValueSource(ints = {1, 2, 3, 4, 5, 7, 8})
    @ParameterizedTest
    void onlyValidExtensions(int version) {
        GameRepository repository = new GameRepository(Zoey.createConfigurationInstance(GAME_DIRECTORY));
        String fileName = RandomStringUtils.randomAlphanumeric(5) + ".z" + version;
        String otherFileName = RandomStringUtils.randomAlphanumeric(5) + ".txt";
        Zoey.createFile(GAME_DIRECTORY + "/" + fileName);
        Zoey.createFile(GAME_DIRECTORY + "/" + otherFileName);

        Collection<String> names = repository.fileNames();

        assertThat(names, hasItem(fileName));
        assertThat(names, not(hasItem(otherFileName)));
    }

    @Test
    void includeSubDirectories() {
        GameRepository repository = new GameRepository(Zoey.createConfigurationInstance(GAME_DIRECTORY));
        String subDirectory = RandomStringUtils.randomAlphabetic(13);
        String fileName = RandomStringUtils.randomAlphanumeric(25) + ".z8";
        Zoey.createDirectory(GAME_DIRECTORY + "/" + subDirectory);
        Zoey.createFile(GAME_DIRECTORY + "/" + subDirectory + "/" + fileName);

        Collection<String> names = repository.fileNames();

        assertThat(names, hasItem(fileName));
    }
}
