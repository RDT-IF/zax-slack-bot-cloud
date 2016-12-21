package org.rdtif.zaxslackbot;

import java.io.IOException;
import javax.inject.Inject;

import com.ullink.slack.simpleslackapi.SlackSession;

class SlackConnection {
    private final SlackSession session;

    @Inject
    SlackConnection(SlackSession session) {
        this.session = session;
    }

    void connect() {
        try {
            session.connect();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
