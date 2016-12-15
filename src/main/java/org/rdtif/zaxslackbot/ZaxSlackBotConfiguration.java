package org.rdtif.zaxslackbot;

import java.util.Properties;

class ZaxSlackBotConfiguration {
    private final Properties properties;

    ZaxSlackBotConfiguration(Properties properties) {
        this.properties = properties;
    }

    String getApiToken() {
        return properties.getProperty("api-token");
    }
}
