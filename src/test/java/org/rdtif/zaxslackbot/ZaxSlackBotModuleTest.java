package org.rdtif.zaxslackbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

class ZaxSlackBotModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());

    @BeforeAll
    static void beforeAll() {
        Zoey.createConfigurationFile();
    }

    @AfterAll
    static void afterAll() {
        Zoey.deleteConfigurationFile();
    }

    @Test
    void providesConfiguration() {
        ZaxSlackBotConfiguration configuration = injector.getInstance(ZaxSlackBotConfiguration.class);

        assertThat(configuration, notNullValue());
    }

    @Test
    void providesSlackConnection() {
        SlackConnection connection = injector.getInstance(SlackConnection.class);

        assertThat(connection, notNullValue());
    }
}
