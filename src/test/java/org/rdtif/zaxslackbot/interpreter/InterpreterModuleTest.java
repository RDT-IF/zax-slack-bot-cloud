package org.rdtif.zaxslackbot.interpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.junit.Test;
import org.rdtif.zaxslackbot.ZaxSlackBotModule;

public class InterpreterModuleTest {
    private final Injector injector = Guice.createInjector(new ZaxSlackBotModule());

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