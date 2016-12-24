package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

class SlackTextScreen extends TextScreen {
    private final SlackSession slackSession;
    private final SlackChannel slackChannel;
    private String slackMessageTimeStamp;

    SlackTextScreen(SlackSession slackSession, SlackChannel slackChannel) {
        this.slackSession = slackSession;
        this.slackChannel = slackChannel;
        this.slackMessageTimeStamp = slackSession.sendMessage(slackChannel, "").getReply().getTimestamp();
    }

    void update() {
        slackMessageTimeStamp = slackSession.updateMessage(slackMessageTimeStamp, slackChannel, getText()).getReply().getTimestamp();
    }
}
