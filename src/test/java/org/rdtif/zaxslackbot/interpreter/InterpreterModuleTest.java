package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.ZaxSlackBotModule;
import org.rdtif.zaxslackbot.Zoey;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

class InterpreterModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());

    @BeforeAll
    static void beforeAll() throws IOException {
        Zoey.createConfigurationFile();
    }

    @AfterAll
    static void afterAll() throws IOException {
        Zoey.deleteConfigurationFile();
    }

    @Test
    void providesActionMap() {
        Map<LanguageAction, Action> map = injector.getInstance(Key.get(new TypeLiteral<Map<LanguageAction, Action>>() {
        }));

        assertThat(map, notNullValue());
    }

    @Test
    void actionMapContainsListGames() {
        Map<LanguageAction, Action> map = injector.getInstance(Key.get(new TypeLiteral<Map<LanguageAction, Action>>() {
        }));
        Action action = map.get(LanguageAction.ListGames);

        assertThat(action, instanceOf(ListGamesAction.class));
    }
}