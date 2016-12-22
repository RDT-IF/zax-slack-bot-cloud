package org.rdtif.zaxslackbot.interpreter;

import java.io.IOException;
import java.util.Map;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.commons.codec.language.bm.Lang;
import org.rdtif.zaxslackbot.GameRepository;

public class InterpreterModule extends AbstractModule {
    private final ObjectMapper mapper = new ObjectMapper();

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

    @Provides
    @Singleton
    public LanguageProcessor providesLanguageProcessor(Map<LanguageAction, Action> actionActionMap) {
        LanguageProcessor languageProcessor = new LanguageProcessor(actionActionMap);
        languageProcessor.registerPattern(loadLanguagePattern("GreetingPattern.json"));
        languageProcessor.registerPattern(loadLanguagePattern("ListGamesPattern.json"));
        languageProcessor.registerPattern(loadLanguagePattern("StartGamesPattern.json"));
        return languageProcessor;
    }

    private LanguagePattern loadLanguagePattern(String filename) {
        try {
            return mapper.readValue(getClass().getClassLoader().getResourceAsStream(filename), LanguagePattern.class);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
