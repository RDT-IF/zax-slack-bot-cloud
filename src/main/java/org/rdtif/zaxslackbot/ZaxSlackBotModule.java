package org.rdtif.zaxslackbot;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.rdtif.zaxslackbot.interpreter.InterpreterModule;
import org.rdtif.zaxslackbot.slack.SlackApplicationModule;
import org.rdtif.zaxslackbot.userinterface.InputState;

import javax.inject.Singleton;

public class ZaxSlackBotModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SlackApplicationModule());
        install(new InterpreterModule());
    }

    @Provides
    @Singleton
    public ZaxSlackBotConfiguration providesConfiguration(ConfigurationLoader configurationLoader) {
        return configurationLoader.getConfigurationFrom(".");
    }

    @Provides
    @Singleton
    public InputState providesInputState() {
        return new InputState();
    }
}
