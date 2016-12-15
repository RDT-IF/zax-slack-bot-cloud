package org.rdtif.zaxslackbot;

class ZaxSlackBot {
    private final ConfigurationLoader configurationLoader = new ConfigurationLoader();

    public static void main(String... arguments) {
        new ZaxSlackBot().run();
    }

    private void run() {
        ZaxSlackBotConfiguration configuration = configurationLoader.getConfigurationFrom(".");
        SlackConnection connection = new SlackConnection(configuration.getApiToken());
        connection.connect();
    }
}
