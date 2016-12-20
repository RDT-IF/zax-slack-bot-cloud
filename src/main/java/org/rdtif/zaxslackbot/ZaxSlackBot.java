package org.rdtif.zaxslackbot;

import com.google.inject.Guice;

import javax.inject.Inject;

class ZaxSlackBot {
    private final SlackConnection connection;

    @Inject
    ZaxSlackBot(SlackConnection connection) {
        this.connection = connection;
    }

    public static void main(String... arguments) {
        Guice.createInjector(new ZaxSlackBotModule()).getInstance(ZaxSlackBot.class).run();
    }


    private void run() {
        connection.connect();
    }
}
