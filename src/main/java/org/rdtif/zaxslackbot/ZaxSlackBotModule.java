package org.rdtif.zaxslackbot;

import javax.inject.Singleton;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.rdtif.zaxslackbot.interpreter.InterpreterModule;

public class ZaxSlackBotModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new InterpreterModule());
    }

    @Provides
    @Singleton
    public ZaxSlackBotConfiguration providesConfiguration(ConfigurationLoader configurationLoader) {
        return configurationLoader.getConfigurationFrom(".");
    }

    @Provides
    @Singleton
    public SlackSession providesSlackSession(ZaxSlackBotConfiguration configuration, GroupJoinListener groupJoinListener, MessagePostedListener messagePostedListener) {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(configuration.getApiToken());
        session.addGroupJoinedListener(groupJoinListener);
        session.addMessagePostedListener(messagePostedListener);
        return session;
    }

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return new EventBus();
    }
}
