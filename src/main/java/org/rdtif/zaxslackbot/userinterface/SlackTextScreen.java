package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

class SlackTextScreen extends TextScreen {
    private final SlackSession slackSession;
    private final SlackChannel slackChannel;
    private String slackMessageTimeStamp;

    SlackTextScreen(SlackSession slackSession, SlackChannel slackChannel) {
        super(new Extent(25, 80));
        this.slackSession = slackSession;
        this.slackChannel = slackChannel;
        this.slackMessageTimeStamp = slackSession.sendMessage(slackChannel, "FOOBARBAZ").getReply().getTimestamp();
    }

    void update() {
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        String message = "```\n" + getJoinedText().replaceAll("```", replaceActualBackTicks) + "\n```";
        slackMessageTimeStamp = slackSession.updateMessage(slackMessageTimeStamp, slackChannel, message).getReply().getTimestamp();
    }
}
