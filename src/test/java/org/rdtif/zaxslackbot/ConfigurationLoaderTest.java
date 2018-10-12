package org.rdtif.zaxslackbot;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class ConfigurationLoaderTest {
    private final ConfigurationLoader configurationLoader = new ConfigurationLoader();

    @Test
    void neverReturnNull() {
        Zoey.createConfigurationFile( "", "");

        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(".");

        assertThat(configuration, notNullValue());
    }

    @Test
    void tokenProperty() {
        String token = RandomStringUtils.randomAlphanumeric(25);
        Zoey.createConfigurationFile(token);

        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(".");

        assertThat(configuration.getApiToken(), equalTo(token));

    }
}