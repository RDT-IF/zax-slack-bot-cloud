package org.rdtif.zaxslackbot.interpreter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.rdtif.zaxslackbot.GameFileRepository;
import org.rdtif.zaxslackbot.ZaxSlackBotConfiguration;

import javax.inject.Singleton;
import java.util.Map;

public class InterpreterModule extends AbstractModule {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Map<LanguageAction, Action> providesActionMap(ZaxSlackBotConfiguration configuration, GameFileRepository repository, ZCpuFactory zCpuFactory, SlackSession session) {
        return ImmutableMap.<LanguageAction, Action>builder()
                .put(LanguageAction.ListGames, new ListGamesAction(repository))
                .put(LanguageAction.StartGame, new StartGameAction(configuration, session, repository, zCpuFactory, new EventBus()))
                .build();
    }

    @Provides
    @Singleton
    public LanguageProcessor providesLanguageProcessor(Map<LanguageAction, Action> actionActionMap, LanguagePatternLoader languagePatternLoader) {
        LanguageProcessor languageProcessor = new LanguageProcessor(actionActionMap);
        languageProcessor.registerPattern(languagePatternLoader.load("GreetingPattern.json"));
        languageProcessor.registerPattern(languagePatternLoader.load("ListGamesPattern.json"));
        languageProcessor.registerPattern(languagePatternLoader.load("StartGamesPattern.json"));
        return languageProcessor;
    }
}
