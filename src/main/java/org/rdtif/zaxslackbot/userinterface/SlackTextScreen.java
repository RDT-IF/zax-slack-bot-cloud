package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;

public class SlackTextScreen extends TextScreen {
    private final SlackDisplayMessageUpdater slackDisplayMessageUpdater;

    public SlackTextScreen(SlackSession slackSession, SlackChannel slackChannel, Extent size) {
        super(size);
        slackDisplayMessageUpdater = new SlackDisplayMessageUpdater(slackSession, slackChannel.getId());
    }

    void initialize(int version) {
        super.setVersion(version);
        slackDisplayMessageUpdater.postMessage("Display loading...");
    }

    public void update() {
        slackDisplayMessageUpdater.updateMessage(getJoinedText());
    }
}
