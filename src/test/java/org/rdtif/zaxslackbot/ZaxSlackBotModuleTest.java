package org.rdtif.zaxslackbot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

public class ZaxSlackBotModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());

    @Test
    public void providesConfiguration() {
        ZaxSlackBotConfiguration configuration = injector.getInstance(ZaxSlackBotConfiguration.class);

        assertThat(configuration, notNullValue());
    }

    @Test
    public void providesSlackConnection() {
        SlackConnection connection = injector.getInstance(SlackConnection.class);

        assertThat(connection, notNullValue());
    }
}
