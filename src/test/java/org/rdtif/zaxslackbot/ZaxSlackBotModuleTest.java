package org.rdtif.zaxslackbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(ConfigurationFileExtension.class)
class ZaxSlackBotModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());

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
