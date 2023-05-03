package org.rdtif.zaxslackbot.slack;

import com.slack.api.bolt.jetty.SlackAppServer;

import javax.inject.Inject;

public class SlackApplicationServer {
    private final SlackAppServer slackAppServer;

    @Inject
    public SlackApplicationServer(SlackAppServer slackAppServer) {
        this.slackAppServer = slackAppServer;
    }

    public void start() {
        try {
            slackAppServer.start();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
