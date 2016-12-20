package org.rdtif.zaxslackbot;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

class ZaxSlackBotModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    public ZaxSlackBotConfiguration providesConfiguration(ConfigurationLoader configurationLoader) {
        return configurationLoader.getConfigurationFrom(".");
    }

    @Provides
    public SlackConnection providesSlackConnection(ZaxSlackBotConfiguration configuration) {
        return new SlackConnection(configuration.getApiToken());
    }

}
