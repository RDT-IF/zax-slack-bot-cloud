package org.rdtif.zaxslackbot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
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

    @Test
    public void providesActionMap() {
        Map<LanguageAction, Action> map = injector.getInstance(Key.get(new TypeLiteral<Map<LanguageAction, Action>>(){}));

        assertThat(map, notNullValue());
    }

    @Test
    public void actionMapContainsListGames() {
        Map<LanguageAction, Action> map = injector.getInstance(Key.get(new TypeLiteral<Map<LanguageAction, Action>>(){}));
        Action action = map.get(LanguageAction.ListGames);

        assertThat(action, instanceOf(ListGamesAction.class));
    }

}
