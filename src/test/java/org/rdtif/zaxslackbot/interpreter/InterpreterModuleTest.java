package org.rdtif.zaxslackbot.interpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rdtif.zaxslackbot.ZaxSlackBotModule;

public class InterpreterModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());
    private static final Path CONFIGURATION_PATH = Paths.get("configuration.properties");

    @BeforeClass
    public static void beforeAll() throws IOException {
        Files.createFile(CONFIGURATION_PATH);
    }

    @AfterClass
    public static void afterAll() throws IOException {
        Files.delete(Paths.get("configuration.properties"));
    }


    @Test
    public void providesActionMap() {
        Map<LanguageAction, Action> map = injector.getInstance(Key.get(new TypeLiteral<Map<LanguageAction, Action>>() {
        }));

        assertThat(map, notNullValue());
    }

    @Test
    public void actionMapContainsListGames() {
        Map<LanguageAction, Action> map = injector.getInstance(Key.get(new TypeLiteral<Map<LanguageAction, Action>>() {
        }));
        Action action = map.get(LanguageAction.ListGames);

        assertThat(action, instanceOf(ListGamesAction.class));
    }
}