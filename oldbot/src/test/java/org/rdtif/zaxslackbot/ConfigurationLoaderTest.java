package org.rdtif.zaxslackbot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(ConfigurationFileExtension.class)
class ConfigurationLoaderTest {
    private final ConfigurationLoader configurationLoader = new ConfigurationLoader();

    @Test
    void neverReturnNull() {
        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(".");

        assertThat(configuration, notNullValue());
    }

    @Test
    void tokenProperty(ZaxSlackBotConfiguration expected) {
        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(".");

        assertThat(configuration.getApiToken(), equalTo(expected.getApiToken()));
    }

    @Test
    void gameDirectoryProperty(ZaxSlackBotConfiguration expected) {
        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(".");

        assertThat(configuration.getGameDirectory(), equalTo(expected.getGameDirectory()));
    }
}