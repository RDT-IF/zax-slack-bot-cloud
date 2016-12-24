package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

public class SlackTextScreen extends TextScreen {
    private final SlackSession slackSession;
    private final SlackChannel slackChannel;
    private String slackMessageTimeStamp;

    public SlackTextScreen(SlackSession slackSession, SlackChannel slackChannel) {
        this.slackSession = slackSession;
        this.slackChannel = slackChannel;
        this.slackMessageTimeStamp = slackSession.sendMessage(slackChannel, "FOOBARBAZ").getReply().getTimestamp();
    }

    public void update() {
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        String message = "```\n" + getText().replaceAll("```", replaceActualBackTicks) + "\n```";
        slackMessageTimeStamp = slackSession.updateMessage(slackMessageTimeStamp, slackChannel, message).getReply().getTimestamp();
    }
}
