package org.rdtif.zaxslackbot;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ZaxSlackBotConfigurationTest {
    @Test
    void getApiTokenFromProperties() {
        ZaxSlackBotConfiguration configuration = createConfiguration();

        assertThat(configuration.getApiToken(), equalTo("apiToken"));
    }

    @Test
    void getSlackClientSecretFromProperties() {
        ZaxSlackBotConfiguration configuration = createConfiguration();

        assertThat(configuration.getSlackClientSecret(), equalTo("slackClientSecret"));
    }

    @Test
    void getSlackSigningSecretFromProperties() {
        ZaxSlackBotConfiguration configuration = createConfiguration();

        assertThat(configuration.getSlackSigningSecret(), equalTo("slackSigningSecret"));
    }

    @Test
    void getGameDirectoryFromPropertiesTrailingSlashPresent() {
        String gameDirectory = RandomStringUtils.randomAlphabetic(13) + "/";
        Properties properties = new Properties();
        properties.setProperty("game-directory", gameDirectory);

        ZaxSlackBotConfiguration configuration = new ZaxSlackBotConfiguration(properties);

        assertThat(configuration.getGameDirectory(), equalTo(gameDirectory));
    }

    @Test
    void getGameDirectoryFromPropertiesWhenTrailingSlashNotPresent() {
        String gameDirectory = RandomStringUtils.randomAlphabetic(13);
        Properties properties = new Properties();
        properties.setProperty("game-directory", gameDirectory);

        ZaxSlackBotConfiguration configuration = new ZaxSlackBotConfiguration(properties);

        assertThat(configuration.getGameDirectory(), equalTo(gameDirectory + "/"));
    }

    private ZaxSlackBotConfiguration createConfiguration() {
        Properties properties = new Properties();
        properties.setProperty("api-token", "apiToken");
        properties.setProperty("slack-client-secret", "slackClientSecret");
        properties.setProperty("slack-signing-secret", "slackSigningSecret");
        return new ZaxSlackBotConfiguration(properties);
    }
}