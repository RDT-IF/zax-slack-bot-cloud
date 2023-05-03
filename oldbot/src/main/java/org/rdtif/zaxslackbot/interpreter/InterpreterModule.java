package org.rdtif.zaxslackbot.interpreter;

import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.rdtif.zaxslackbot.GameFileRepository;
import org.rdtif.zaxslackbot.ZaxSlackBotConfiguration;
import org.rdtif.zaxslackbot.slack.SlackClient;

import javax.inject.Singleton;
import java.util.Map;

public class InterpreterModule extends AbstractModule {
    @Provides
    @Singleton
    public Map<LanguageAction, Action> providesActionMap(ZaxSlackBotConfiguration configuration, GameFileRepository repository, ZCpuFactory zCpuFactory, SlackClient slackClient) {
        return ImmutableMap.<LanguageAction, Action>builder()
                .put(LanguageAction.ListGames, new ListGamesAction(repository))
                .put(LanguageAction.StartGame, new StartGameAction(configuration, repository, zCpuFactory, slackClient))
                .build();
    }

    @Provides
    @Singleton
    public Interpreter providesLanguageProcessor(Map<LanguageAction, Action> actionActionMap, LanguagePatternLoader languagePatternLoader, SlackClient slackClient) {
        Interpreter interpreter = new Interpreter(actionActionMap, slackClient);
        interpreter.registerPattern(languagePatternLoader.load("GreetingPattern.json"));
        interpreter.registerPattern(languagePatternLoader.load("ListGamesPattern.json"));
        interpreter.registerPattern(languagePatternLoader.load("StartGamesPattern.json"));
        return interpreter;
    }
}
