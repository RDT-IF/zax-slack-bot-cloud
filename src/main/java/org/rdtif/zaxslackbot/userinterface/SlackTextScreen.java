package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;

public class SlackTextScreen extends TextScreen {
    private final SlackSession slackSession;
    private final SlackChannel slackChannel;
    private String slackMessageTimeStamp;

    public SlackTextScreen(SlackSession slackSession, SlackChannel slackChannel, Extent size) {
        super(size);
        this.slackSession = slackSession;
        this.slackChannel = slackChannel;
    }

    void initialize(int version) {
        super.setVersion(version);
        slackMessageTimeStamp = slackSession.sendMessage(slackChannel, "Display loading...").getReply().getTimestamp();
    }

    void update() {
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        String message = "```\n" + getJoinedText().replaceAll("```", replaceActualBackTicks) + "\n```";
        SlackMessageHandle<SlackMessageReply> slackMessageReplySlackMessageHandle = slackSession.updateMessage(slackMessageTimeStamp, slackChannel, message);
        SlackMessageReply reply = slackMessageReplySlackMessageHandle.getReply();
        slackMessageTimeStamp = reply.getTimestamp();
    }
}
