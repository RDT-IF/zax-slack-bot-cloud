package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

class SlackTextScreen extends TextScreen {
    private final SlackSession slackSession;
    private final SlackChannel slackChannel;
    private String slackMessageTimeStamp;

    SlackTextScreen(SlackSession slackSession, SlackChannel slackChannel, Extent size) {
        super(size);
        this.slackSession = slackSession;
        this.slackChannel = slackChannel;
    }

    void initialize() {
        slackMessageTimeStamp = slackSession.sendMessage(slackChannel, "Display loading...").getReply().getTimestamp();
    }

    void update() {
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        String message = "```\n" + getJoinedText().replaceAll("```", replaceActualBackTicks) + "\n```";
        slackMessageTimeStamp = slackSession.updateMessage(slackMessageTimeStamp, slackChannel, message).getReply().getTimestamp();
    }
}
