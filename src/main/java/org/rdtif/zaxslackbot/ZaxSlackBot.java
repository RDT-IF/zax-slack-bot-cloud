package org.rdtif.zaxslackbot;

import javax.inject.Inject;

import com.google.inject.Guice;

class ZaxSlackBot {
    public static void main(String... arguments) {
        Guice.createInjector(new ZaxSlackBotModule()).getInstance(ZaxSlackBot.class).run();
    }

    private final SlackConnection connection;

    @Inject
    ZaxSlackBot(SlackConnection connection) {
        this.connection = connection;
    }

    void run() {
        connection.connect();
    }
}
