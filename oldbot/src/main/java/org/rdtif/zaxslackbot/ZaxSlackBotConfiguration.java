package org.rdtif.zaxslackbot;

import java.util.Properties;

public class ZaxSlackBotConfiguration {
    private final Properties properties;

    public ZaxSlackBotConfiguration(Properties properties) {
        this.properties = properties;
    }

    public String getGameDirectory() {
        String gameDirectory = properties.getProperty("game-directory");
        return gameDirectory.endsWith("/") ? gameDirectory : gameDirectory + "/";
    }

    public String getSlackClientSecret() {
        return properties.getProperty("slack-client-secret");
    }

    public String getSlackSigningSecret() {
        return properties.getProperty("slack-signing-secret");
    }

    public String getApiToken() {
        return properties.getProperty("api-token");
    }
}
