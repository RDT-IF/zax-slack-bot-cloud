package org.rdtif.zaxslackbot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.Random;

@RunWith(Theories.class)
public class GameRepositoryTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void fileNamesNeverReturnsNull() {
        GameRepository repository = new GameRepository(createConfiguration());

        Collection<String> stream = repository.fileNames();

        assertThat(stream, notNullValue());
    }

    @Test
    public void fileNamesContainsOneFile() {
        GameRepository repository = new GameRepository(createConfiguration());
        String fileName = RandomStringUtils.randomAlphanumeric(25) + ".z8";
        createFile(temporaryFolder.getRoot(), fileName);
        Collection<String> names = repository.fileNames();
        assertThat(names, hasItem(fileName));
    }

    @Theory
    public void onlyValidExtensions(Integer version) {
        GameRepository repository = new GameRepository(createConfiguration());
        String fileName = RandomStringUtils.randomAlphanumeric(5) + ".z" + version;
        String otherFileName = RandomStringUtils.randomAlphanumeric(5) + ".txt";
        createFile(temporaryFolder.getRoot(), fileName);
        createFile(temporaryFolder.getRoot(), otherFileName);
        Collection<String> names = repository.fileNames();
        assertThat(names, hasItem(fileName));
        assertThat(names, not(hasItem(otherFileName)));
    }

    @DataPoints public static Collection<Integer> datapoints() {
        return Arrays.asList(1,2,3,4,5,7,8);
    }

    @Test
    public void includeSubDirectories() {
        GameRepository repository = new GameRepository(createConfiguration());
        String fileName = RandomStringUtils.randomAlphanumeric(25) + ".z8";
        createFile(createFolder(), fileName);
        Collection<String> names = repository.fileNames();
        assertThat(names, hasItem(fileName));
    }

    private File createFolder() {
        try {
            return temporaryFolder.newFolder();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void createFile(File folder, String fileName) {
        try {
            boolean newFile = new File(folder, fileName).createNewFile();
            if (!newFile) {
                throw new RuntimeException();
            }
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