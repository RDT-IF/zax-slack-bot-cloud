package org.rdtif.zaxslackbot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

class SlackConnection {
    private final SlackSession session;

    SlackConnection(String apiToken) {
        session = SlackSessionFactory.createWebSocketSlackSession(apiToken);

    }

    void connect() {
        try {
            session.connect();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
