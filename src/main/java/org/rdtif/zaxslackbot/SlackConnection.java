package org.rdtif.zaxslackbot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;
import javax.inject.Inject;

class SlackConnection {
    private final SlackSession session;

    @Inject
    SlackConnection(String apiToken) {
        session = SlackSessionFactory.createWebSocketSlackSession(apiToken);
        session.addGroupJoinedListener(new GroupJoinListener());
        session.addMessagePostedListener(new MessagePostedListener());
    }

    void connect() {
        try {
            session.connect();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
