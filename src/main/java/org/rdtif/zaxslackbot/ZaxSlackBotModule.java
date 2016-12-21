package org.rdtif.zaxslackbot;

import java.util.Map;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

class ZaxSlackBotModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public ZaxSlackBotConfiguration providesConfiguration(ConfigurationLoader configurationLoader) {
        return configurationLoader.getConfigurationFrom(".");
    }

    @Provides
    @Singleton
    public SlackSession providesSlackConnection(ZaxSlackBotConfiguration configuration, GroupJoinListener groupJoinListener, MessagePostedListener messagePostedListener) {
            SlackSession session = SlackSessionFactory.createWebSocketSlackSession(configuration.getApiToken());
            session.addGroupJoinedListener(groupJoinListener);
            session.addMessagePostedListener(messagePostedListener);
            return session;
    }

    @Provides
    @Singleton
    Map<LanguageAction, Action> providesActionMap() {
        return ImmutableMap.<LanguageAction, Action>builder()
                .put(LanguageAction.ListGames, new ListGamesAction())
                .build();
    }
}
