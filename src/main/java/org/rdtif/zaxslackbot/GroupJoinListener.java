package org.rdtif.zaxslackbot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackGroupJoined;
import com.ullink.slack.simpleslackapi.listeners.SlackGroupJoinedListener;

public class GroupJoinListener implements SlackGroupJoinedListener {
    @Override
    public void onEvent(SlackGroupJoined event, SlackSession session) {
        SlackChannel channel = event.getSlackChannel();
        session.sendMessage(channel, "Hi Everyone! Would you like to play a game?");
    }
}
