package org.rdtif.zaxslackbot.interpreter;

import java.util.Map;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.rdtif.zaxslackbot.GameRepository;

public class InterpreterModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Map<LanguageAction, Action> providesActionMap(GameRepository repository) {
        return ImmutableMap.<LanguageAction, Action>builder()
                .put(LanguageAction.ListGames, new ListGamesAction(repository))
                .build();
    }
}
