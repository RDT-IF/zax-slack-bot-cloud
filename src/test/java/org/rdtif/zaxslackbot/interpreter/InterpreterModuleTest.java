package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rdtif.zaxslackbot.ConfigurationFileExtension;
import org.rdtif.zaxslackbot.ZaxSlackBotModule;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(ConfigurationFileExtension.class)
class InterpreterModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());

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