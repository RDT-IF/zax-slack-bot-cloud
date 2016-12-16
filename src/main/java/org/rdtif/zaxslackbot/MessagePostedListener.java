package org.rdtif.zaxslackbot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

public class MessagePostedListener implements SlackMessagePostedListener{
    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        if(!event.getSender().isBot()) {
            session.sendMessage(event.getChannel(), "I don't understand that yet.");
        }
    }
}
